package datum.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import datum.Main;
import datum.config.JwtService;
import datum.email.EmailDetails;
import datum.email.EmailService;
import datum.token.*;
import datum.user.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email.trim().toLowerCase());
    }

    public String register(HttpServletRequest headRequest, UserDTO userDTO) {
        return register(headRequest, UserMapper.INSTANCE.convert(userDTO));
    }

    public String register(HttpServletRequest request, User user) {
        try {
            if (user.getEmail().isBlank())
                throw new IllegalStateException("Введите email");
            if (!Main.isValidEmail(user.getEmail()))
                throw new IllegalStateException("Неправильный email");
            if (checkEmail(user.getEmail()))
                throw new IllegalStateException("Пользователь уже зарегистрирован");
            if (user.getPassword().isBlank())
                throw new IllegalStateException("Нет пароля");
//            user.setRole(Role.USER);

            userRepository.save(user);

            String token = UUID.randomUUID().toString();
            var confirmationToken = ConfirmationToken
                    .builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .build();
            confirmationToken.setUser(user);
            confirmationTokenRepository.save(confirmationToken);

            String mainLink = "http://" + request.getServerName();
            String link = mainLink +
                          ":" + request.getServerPort() + request.getContextPath() +
                          "/api/v1/auth/confirm?token=" + token;
            var emailDetails = EmailDetails.builder()
                    .recipient(user.getEmail())
                    .subject("Подтверждение адреса электронной почты")
                    .build();
            emailService.sendSimpleMail(emailDetails, mainLink, link);
            System.out.println(link);
            return token + " " + link;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("ощибка сохранение " + e);
        }
    }

//    @Transactional
    public AuthenticationResponse confirmToken(String token) {
        var confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("токен не найден"));
        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("адрес электронной почты уже подтвержден");
        }
        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("срок действия токена истек");
        }
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
        userRepository.enableUser(confirmationToken.getUser().getEmail());
        var jwtToken = jwtService.generateToken(confirmationToken.getUser());
        var refreshToken = jwtService.generateRefreshToken(confirmationToken.getUser());
        saveUserToken(confirmationToken.getUser(), jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail().toLowerCase().trim(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmailIgnoreCase(request.getEmail().toLowerCase().trim())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
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

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String email;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        email = jwtService.extractEmail(refreshToken);
        if (email != null) {
            var user = this.userRepository.findByEmailIgnoreCase(email)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public String resetPassword(HttpServletRequest request, String email) {
        var user = userRepository.findByEmailIgnoreCase(
                email.toLowerCase().trim()).orElseThrow();
        String token = UUID.randomUUID().toString();
        var myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(user);
        passwordResetTokenRepository.save(myToken);
        String contextPath = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String url = contextPath + "/api/v1/auth/forgotPassword?id=" + user.getId() + "&token=" + token;
        var emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Сброс пароля")
                .build();
        return emailService.sendSimpleMail(emailDetails, contextPath, url) + " " + url;
    }

    public String forgotPassword(Long id, String token) {
        var passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || !passwordResetToken.getUser().getId().equals(id)) {
            throw new IllegalStateException("неправильный токен");
        }
        var cal = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new IllegalStateException("срок действия токена истек");
        }
        passwordResetToken.getUser().setPassword(null);
        userRepository.save(passwordResetToken.getUser());
        return passwordResetToken.getToken();
    }

    public Boolean setPassword(ForgotPassword password) {
        var passwordResetToken = passwordResetTokenRepository.findByToken(password.getToken());
        if (passwordResetToken == null) {
            throw new IllegalStateException("такого токена нет");
        }
        var cal = Calendar.getInstance();
        if ((passwordResetToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new IllegalStateException("срок действия токена истек");
        }
        var user = passwordResetToken.getUser();
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(user);
        passwordResetToken.setToken(null);
        return true;
    }

    public Boolean changePassword(Password password) {
        var user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        if (!passwordEncoder.matches(password.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("Ощибка");
        }
        user.setPassword(passwordEncoder.encode(password.getPassword()));
        userRepository.save(user);
        return true;
    }
}
