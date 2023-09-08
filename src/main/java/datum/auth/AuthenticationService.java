package datum.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import datum.config.JwtService;
import datum.email.EmailDetails;
import datum.email.EmailService;
import datum.token.*;
import datum.user.Person;
import datum.user.Role;
import datum.user.User;
import datum.user.UserRepository;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email.trim());
    }

    private static boolean isDate(String s) {
        return s.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])");
    }

    public static boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public String register(
            HttpServletRequest headRequest,
            @RequestBody RegisterRequest request
    ) {
        if (!isValidEmail(request.getEmail())) {
            throw new IllegalStateException("Неправильный email");
        }
        if (checkEmail(request.getEmail())) {
            throw new IllegalStateException("Пользователь уже зарегистрирован");
        }
        Date birthDay = null;
        if (isDate(request.getBirthDay()))
            birthDay = Date.valueOf(request.getBirthDay());
        else throw new IllegalStateException("не правильный формат даты {2020-12-30}");
        try {
            var user = User.builder()
                    .email(request.getEmail().trim())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();

            var person = Person.builder()
                    .firstname(request.getFirstname())
                    .surname(request.getSurname())
                    .patronymic(request.getPatronymic())
                    .address(request.getAddress())
                    .birthDay(birthDay)
                    .phone(request.getPhone())
                    .email(request.getEmail())
                    .build();

            user.setPerson(person);

            userRepository.save(user);

            String token = UUID.randomUUID().toString();
            var confirmationToken = ConfirmationToken
                    .builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .build();
            confirmationToken.setUser(user);
            confirmationTokenService.saveConfirmationToken(confirmationToken);

            String mainLink = "http://" + headRequest.getServerName();
            String link = mainLink +
                    ":" + headRequest.getServerPort() + headRequest.getContextPath() +
                    "/api/v1/auth/confirm?token=" + token;
            var emailDetails = EmailDetails.builder()
                    .recipient(request.getEmail())
                    .subject("Подтверждение адреса электронной почты")
                    .build();
            return emailService.sendSimpleMail(emailDetails, mainLink, link);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("ощибка сохранение " + e);
//            throw new IllegalArgumentException("ощибка сохранение "+e);
        }
    }

    @Transactional
    public AuthenticationResponse confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("токен не найден"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("адрес электронной почты уже подтвержден");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("срок действия токена истек");
        }

        confirmationTokenService.setConfirmedAt(token);
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
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmailIgnoreCase(request.getEmail())
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

    public String resetPassword(HttpServletRequest request, EmailRequest email) {
        var user = userRepository.findByEmailIgnoreCase(email.getEmail()).orElseThrow();

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
        return emailService.sendSimpleMail(emailDetails, contextPath, url);
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
        return passwordResetToken.getToken();
    }

    public Boolean setNewPassword(HttpServletRequest request, NewPasswordRequest newPassword) {
        var user = passwordResetTokenRepository.findByToken(newPassword.getToken()).getUser();
        user.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(user);
        return true;
    }

    public Boolean changePassword(HttpServletRequest request, ChangePasswordRequest changePasswordRequest) {
        var user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
//        SecurityContextHolder.getContext().getAuthentication();
        if (!passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("Ощибка");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getPassword()));
        userRepository.save(user);
        return true;
    }

//    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
//        return passwordEncoder.matches(oldPassword, user.getPassword());
//    }
//
//    public void changeUserPassword(final User user, final String password) {
//        user.setPassword(passwordEncoder.encode(password));
//        userRepository.save(user);
//    }
}
