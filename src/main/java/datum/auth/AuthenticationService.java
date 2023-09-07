package datum.auth;

import datum.config.JwtService;
//import datum.email.EmailSender;
import datum.email.EmailDetails;
import datum.email.EmailService;
import datum.token.*;
import datum.user.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
//import datum.auth.EmailValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;
    private final EmailService emailService;
//    private final EmailSender emailSender;
//    private final UserService userService;

    public Boolean checkEmail(EmailRequest request) {

        return !userRepository.existsByEmailIgnoreCase(request.getEmail().trim());
    }

    private static boolean isDate(String s) {
        return s.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])");
    }

    public String register(
            HttpServletRequest headRequest,
            @RequestBody RegisterRequest request
    ) {
        boolean isValidEmail = emailValidator.
                test(request.getEmail());

        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
        Date birthDay = null;
        if (isDate(request.getBirthDay()))
            birthDay = Date.valueOf(request.getBirthDay());

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
//        System.out.println(savedUser);
//        var jwtToken = jwtService.generateToken(user);
//        var refreshToken = jwtService.generateRefreshToken(user);
//        saveUserToken(savedUser, jwtToken);

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
//                .msgBody(emailService.buildConfirmMessage(mainLink,link))
                .subject("Подтверждение адреса электронной почты")
                .build();
//        String bodyMessage = buildEmail(request.getFirstname(), link);
//        var emailDetails = new EmailDetails();
//        emailDetails.setRecipient(request.getEmail());
//        emailDetails.setMsgBody(mainLink,link);
//        emailDetails.setSubject("Подтверждение адреса электронной почты");

        emailService.sendSimpleMail(emailDetails, mainLink, link);
//        emailSender.send(request.getEmail(), buildEmail(request.getFirstname(), link));
//        System.out.println(link);
//        System.out.println(buildEmail(request.getFirstname(), link));

//        return AuthenticationResponse.builder()
//                .accessToken(jwtToken)
//                .refreshToken(refreshToken)
//                .build();
        return "Вам необходимо подтвердить адрес электронной почты " + request.getEmail();
    }

//    public String sendMail(EmailDetails details) {
//        return emailService.sendSimpleMail(details);
//    }

    @Transactional
    public AuthenticationResponse confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
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
//        return confirmationToken.getUser().toString();
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

}
