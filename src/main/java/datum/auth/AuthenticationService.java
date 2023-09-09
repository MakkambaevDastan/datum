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
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PersonService personService;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public Boolean checkEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email.trim().toLowerCase());
    }

//    public static boolean isDateBithDay(String s) {
//        return s.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])");
//    }
//
//    public static boolean isValidEmail(String emailAddress) {
//        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
//        return Pattern.compile(regexPattern)
//                .matcher(emailAddress)
//                .matches();
//    }

    public String register(
            HttpServletRequest headRequest,
            @RequestBody UserDTO userDTO
    ) {
        try {
            if (userDTO.getEmail().isBlank())
                throw new IllegalStateException("Введите email");
            if (!Main.isValidEmail(userDTO.getEmail()))
                throw new IllegalStateException("Неправильный email");
            if (checkEmail(userDTO.getEmail()))
                throw new IllegalStateException("Пользователь уже зарегистрирован");
            User user = UserMapper.INSTANCE.convert(userDTO);
            userRepository.save(user);
//
////        Date birthDay = null;
//        PersonDTO personDTO = userDTO.getPerson();
////        if(personDTO.getBirthDay()!=null)
////            if (isDate(personDTO.getBirthDay()))
////                birthDay = Date.valueOf(personDTO.getBirthDay());
////            else throw new IllegalStateException("не правильный формат даты {YYYY-MM-DD}");
//
//            var user = User.builder()
//                    .email(userDTO.getEmail().trim())
//                    .password(passwordEncoder.encode(userDTO.getPassword()))
//                    .role(Role.USER)
//                    .build();
////            personService.person(personDTO);
////            var person = Person.builder()
////                    .firstname(personDTO.getFirstname())
////                    .surname(personDTO.getSurname())
////                    .patronymic(personDTO.getPatronymic())
////                    .address(personDTO.getAddress())
////                    .birthDay(birthDay)
////                    .phone(personDTO.getPhone())
////                    .email(userDTO.getEmail())
////                    .build();
//
//            user.setPerson(personService.person(personDTO));
//
//            userRepository.save(user);


            String token = UUID.randomUUID().toString();
            var confirmationToken = ConfirmationToken
                    .builder()
                    .token(token)
                    .createdAt(LocalDateTime.now())
                    .expiresAt(LocalDateTime.now().plusMinutes(15))
                    .build();
            confirmationToken.setUser(user);
            confirmationTokenRepository.save(confirmationToken);
//            confirmationTokenService.saveConfirmationToken(confirmationToken);

            String mainLink = "http://" + headRequest.getServerName();
            String link = mainLink +
                          ":" + headRequest.getServerPort() + headRequest.getContextPath() +
                          "/api/v1/auth/confirm?token=" + token;
            var emailDetails = EmailDetails.builder()
                    .recipient(userDTO.getEmail())
                    .subject("Подтверждение адреса электронной почты")
                    .build();
            emailService.sendSimpleMail(emailDetails, mainLink, link);
            return token+" "+link;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("ощибка сохранение " + e);
        }
    }

//    private Person person(PersonDTO personDTO) {
//        Date birthDay = null;
//        if (personDTO.getBirthDay() != null)
//            if (isDate(personDTO.getBirthDay()))
//                birthDay = Date.valueOf(personDTO.getBirthDay());
//            else throw new IllegalStateException("не правильный формат даты {YYYY-MM-DD}");
//
//        var person = Person.builder()
//                .firstname(personDTO.getFirstname())
//                .surname(personDTO.getSurname())
//                .patronymic(personDTO.getPatronymic())
//                .address(personDTO.getAddress())
//                .birthDay(birthDay)
//                .phone(personDTO.getPhone())
//                .email(personDTO.getEmail())
//                .build();
//        personRepository.save(person);
//        return person;
//    }

    @Transactional
    public AuthenticationResponse confirmToken(String token) {
        var confirmationToken = confirmationTokenRepository.findByToken(token).orElseThrow(() ->
                new IllegalStateException("токен не найден"));
//        ConfirmationToken confirmationToken = confirmationTokenService
//                .getToken(token)
//                .orElseThrow(() ->
//                        new IllegalStateException("токен не найден"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("адрес электронной почты уже подтвержден");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("срок действия токена истек");
        }
        confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
//        confirmationTokenService.setConfirmedAt(token);
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
//if(use)
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
        return emailService.sendSimpleMail(emailDetails, contextPath, url)+" "+url;
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
    public Boolean changePassword(PasswordDTO passwordDTO) {
        var user = userRepository.findByEmailIgnoreCase(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), user.getPassword())) {
            throw new IllegalStateException("Ощибка");
        }
        user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
        userRepository.save(user);
        return true;
    }
}
