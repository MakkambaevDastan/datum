package datum.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import datum.Main;
import datum.config.JwtService;
import datum.email.EmailDetails;
import datum.email.EmailService;
import datum.exception.BadRequest;
import datum.exception.EntityNotFound;
import datum.exception.Message;
import datum.token.*;
import datum.user.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;


    public boolean checkEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email.trim().toLowerCase());
    }

    public User register(HttpServletRequest headRequest, UserDTO userDTO) {
        return register(headRequest, UserMapper.INSTANCE.convert(userDTO));
    }

    public User register(HttpServletRequest request, User user) {
        if (user.getEmail().isBlank()) throw new BadRequest("Введите email");
        if (!Main.isValidEmail(user.getEmail())) throw new BadRequest("Неправильный email");
        if (checkEmail(user.getEmail())) throw new BadRequest("Вы уже зарегистрированы войдите");
        userRepository.save(user);
        var accessToken = jwtService.generateConfirmToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, accessToken, refreshToken);
        String link = "http://" + request.getServerName() + ":" + request.getServerPort() +
                      request.getContextPath() + "/api/v1/auth/confirm/" + refreshToken;
        String cancel = "http://" + request.getServerName() + ":" + request.getServerPort() +
                        request.getContextPath() + "/api/v1/auth/cancel/" + refreshToken;
        String body = "Подтвердите свой электронный адрес " + link + "\n Если это были не вы отмените " + cancel;
        var details = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Подтверждение")
                .msgBody(body)
                .build();
        emailService.sendSimpleMail(details);
        System.out.println(link);
//        System.out.println(request.getCookies());
        return user;
    }

    public Object cancel(String token) {
        User user = getUserFromToken(token);
        userRepository.delete(user);
        return true;
    }

    public AuthenticationResponse confirm(String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(updateAccessToken(refreshToken))
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase().trim(),
                        request.getPassword()
                ));
        User user = userRepository.findByEmailIgnoreCase(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new EntityNotFound(Message.USER_NOT_FOUND));
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String accessToken, String refreshToken) {
        var token = Token.builder()
                .user(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private String updateAccessToken(String refreshToken) {
        Token token;
        User user = getUserFromToken(refreshToken);
        if (jwtService.isTokenValid(refreshToken, user)) {
            token = tokenRepository.findByByRefreshToken(refreshToken)
                    .orElseThrow(() -> new EntityNotFound("Токен не найден"));
            if (jwtService.isTokenValid(token.getAccessToken(), user)) {
                token.setAccessToken(jwtService.generateToken(user));
                tokenRepository.save(token);
            } else throw new BadRequest("Срок действия токена истек");
        } else throw new BadRequest("Невалидный токен");
        return token.getAccessToken();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private User getUserFromToken(String token) {
        return userRepository.findByEmailIgnoreCase(jwtService.extractEmail(token))
                .orElseThrow(() -> new EntityNotFound(Message.USER_NOT_FOUND));
    }

    public void accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var map = getMapUserAndRefreshTokenFromRequest(request);
        if (!map.isEmpty()) {
            var refreshToken = map.entrySet().iterator().next().getValue();

            Token token = tokenRepository.findByByRefreshToken(refreshToken)
                    .orElseThrow(() -> new EntityNotFound("Токен не найден"));
            String newAccessToken = updateAccessToken(refreshToken);
            token.setAccessToken(newAccessToken);
            tokenRepository.save(token);
            var authResponse = AuthenticationResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(refreshToken)
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        var map = getMapUserAndRefreshTokenFromRequest(request);
        if (!map.isEmpty()) {
            var entry = map.entrySet().iterator().next();
            User user = entry.getKey();
            String refreshToken = entry.getValue();

            String accessToken = jwtService.generateToken(user);
            String newRefreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, accessToken, newRefreshToken);
            var authResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
            new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
        }
    }

    public boolean refreshToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new BadRequest("Нет токена");
        final String refreshToken = authHeader.substring(7);
        return tokenRepository.findByByRefreshToken(refreshToken)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);
    }

    private Map<User, String> getMapUserAndRefreshTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) return Collections.emptyMap();
        final String refreshToken = authHeader.substring(7);
        final String email = jwtService.extractEmail(refreshToken);
        if (email != null) {
            User user = userRepository.findByEmailIgnoreCase(email)
                    .orElseThrow(() -> new EntityNotFound(Message.USER_NOT_FOUND));
            boolean isTokenValid = tokenRepository.findByByRefreshToken(refreshToken)
                    .map(t -> !t.isExpired() && !t.isRevoked())
                    .orElse(false);
            if (jwtService.isTokenValid(refreshToken, user) && isTokenValid)
                return Collections.singletonMap(user, refreshToken);
        }
        return Collections.emptyMap();
    }

    public String resetPassword(HttpServletRequest request, String email) {
        var user = userRepository.findByEmailIgnoreCase(email.toLowerCase().trim())
                .orElseThrow(() -> new EntityNotFound(Message.USER_NOT_FOUND));
        var accessToken = jwtService.generateConfirmToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);
        String link = "http://" + request.getServerName() + ":" + request.getServerPort() +
                      request.getContextPath() + "/api/v1/auth/forgotPassword/" + refreshToken;
        String cancel = "http://" + request.getServerName() + ":" + request.getServerPort() +
                        request.getContextPath() + "/api/v1/auth/forgotPassword/cancel/" + refreshToken;
        String body = "Запрос на сброс пароля " + link + "\n Если это были не вы отмените " + cancel;
        var details = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Сброс")
                .msgBody(body)
                .build();
        emailService.sendSimpleMail(details);
        return email;
    }

    public AuthenticationResponse forgotPasswordCancel(String token) {
        User user = getUserFromToken(token);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken, refreshToken);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String forgotPassword(String refreshToken) {
        User user = getUserFromToken(refreshToken);
        user.setPassword(null);
        userRepository.save(user);
        return refreshToken;
    }

    public AuthenticationResponse setPassword(ForgotPassword password) {
        User user = getUserFromToken(password.getToken());
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .accessToken(updateAccessToken(password.getToken()))
                .refreshToken(password.getToken())
                .build();
    }

    public Boolean changePassword(Password password) {
        var user = userRepository.findByEmailIgnoreCase(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFound(Message.USER_NOT_FOUND));
        if (!passwordEncoder.matches(password.getOldPassword(), user.getPassword()))
            throw new BadRequest("Не правильный старый пароль");
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(user);
        return true;
    }


}
