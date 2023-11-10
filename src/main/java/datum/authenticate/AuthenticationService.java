package datum.authenticate;

import com.fasterxml.jackson.databind.ObjectMapper;
import datum.Main;
import datum.authenticate.token.TokenService;
import datum.config.JwtService;
import datum.config.email.EmailDetails;
import datum.config.email.EmailService;
import datum.config.exception.ExceptionApp;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenService tokenService;


    public boolean checkEmail(String email) {
        return userRepository.existsByEmailIgnoreCase(email.trim().toLowerCase());
    }

    public boolean checkEmail(HttpServletRequest request) {
        final String[] email = headerDecode64(request);
        return checkEmail(email[0]);

    }

    public void register(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 2)
            throw new ExceptionApp(400, "Введите данные");
        if (!Main.isValidEmail(header[0]))
            throw new ExceptionApp(400, "Неправильный email");
        if (checkEmail(header[0]))
            throw new ExceptionApp(400, "Вы уже зарегистрированы войдите");
        if (header[1].equals("") || header[1].equals(" "))
            throw new ExceptionApp(400, "Пароль не может быть пустым");
        if (header[1].contains(" "))
            throw new ExceptionApp(400, "Пароль содержить пробел");
        User user = User.builder()
                .email(header[0])
                .password(passwordEncoder.encode(header[1]))
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), register(user));
    }

    public Integer register(User user) {
        Integer code = Main.random.nextInt(100000, 999999);
        user.setRole(Role.USER);
        user.setCode(code);
        user.setEnabled(false);
        user.setLocked(true);
        user.setDeleted(false);
        if (user.getId() != null)
            tokenService.deleteAllUserTokens(user);
        userRepository.save(user);
        var details = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("Код подтверждение")
                .msgBody(code.toString())
                .build();
        emailService.sendSimpleMail(details);
        System.out.println(code);
        return code;
    }

    public void confirm(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String[] header = headerDecode64(request);
        if (header.length != 2)
            throw new ExceptionApp(400, "Данные не полны");
        Integer code = Main.parseInt(header[1]);
        final User user = userRepository.findByEmailIgnoreCase(header[0])
                .orElseThrow(() -> new ExceptionApp(404, "Пользователь не найден"));
        if (!user.getCode().equals(code))
            throw new ExceptionApp(400, "Неправильный код");
//        if (!passwordEncoder.matches(header[1], user.getPassword()))
//            throw new ExceptionApp(400, "Неправильный пароль");
        user.setEnabled(true);
        user.setLocked(false);
        user.setCode(null);
        userRepository.save(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtService.generateToken(user));
        response.addHeader("refreshToken", refreshToken);
        tokenService.saveUserToken(user, refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), true);
    }

    public void authenticate(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 2)
            throw new ExceptionApp(400, "Данные не полны");
        final User user = userRepository.findByEmailIgnoreCase(header[0])
                .orElseThrow(() -> new ExceptionApp(400, "Не правильный email или пароль"));
        if (!passwordEncoder.matches(header[1], user.getPassword()))
            throw new ExceptionApp(400, "Не правильный email или пароль");
        if (user.getLocked() && user.getCode() != null)
            throw new ExceptionApp(400, "Пользователь не активирован");
        String refreshToken = jwtService.generateRefreshToken(user);
        response.setHeader(HttpHeaders.AUTHORIZATION, jwtService.generateToken(user));
        response.addHeader("refreshToken", refreshToken);
        tokenService.saveUserToken(user, refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), true);
    }

    public void resetPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 1)
            throw new ExceptionApp(400, "Введите email");
        User user = userRepository.findByEmailIgnoreCase(header[0])
                .orElseThrow(() -> new ExceptionApp(404, "Пользователь не найден"));
        new ObjectMapper().writeValue(response.getOutputStream(), register(user));
    }

    public void cancelResetPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 2)
            throw new ExceptionApp(400, "Введите email или пароль");
        User user = userRepository.findByEmailIgnoreCase(header[0])
                .orElseThrow(() -> new ExceptionApp(404, "Пользователь не найден"));
        if (!passwordEncoder.matches(header[1], user.getPassword()))
            throw new ExceptionApp(400, "Неправильный пароль");
        user.setCode(null);
        user.setEnabled(true);
        user.setLocked(false);
        tokenService.deleteAllUserTokens(user);
        userRepository.save(user);
        authenticate(request, response);
    }

    public Integer forgotPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 2)
            throw new ExceptionApp(400, "Данные не полны");
        Integer code = null;
        if (Main.isInteger(header[1]))
            code = Integer.valueOf(header[1]);
        User user = userRepository.findByEmailIgnoreCaseAndCode(header[0], code)
                .orElseThrow(() -> new ExceptionApp(400, "Не правильные данные"));
        code = Main.random.nextInt(100000, 999999);
        user.setPassword(null);
        user.setCode(code);
        userRepository.save(user);
        return code;
    }

    public void setPassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 3)
            throw new ExceptionApp(400, "Данные не полны");
        Integer code = Main.parseInt(header[2]);
        String email = header[0];
        String password = header[1];
        if (password == null)
            throw new ExceptionApp(400, "Введите пароль");
        if (password.equals("") || password.equals(" "))
            throw new ExceptionApp(400, "Пароль не может быть пустым");
        if (password.contains(" "))
            throw new ExceptionApp(400, "Пароль содержить пробел");
        User user = userRepository.findByEmailIgnoreCaseAndCode(email, code)
                .orElseThrow(() -> new ExceptionApp(404, "Пользователь не найден"));
        user.setPassword(passwordEncoder.encode(password));
        user.setCode(null);
        user.setEnabled(true);
        user.setLocked(false);
        tokenService.deleteAllUserTokens(user);
        userRepository.save(user);
        authenticate(request, response);
    }

    public void changePassword(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        String[] header = headerDecode64(request);
        if (header.length != 3)
            throw new ExceptionApp(400, "Данные не полны");
        User user = userRepository.findByEmailIgnoreCase(header[0])
                .orElseThrow(() -> new ExceptionApp(404, "Пользователь не найден"));
        if (!passwordEncoder.matches(header[1], user.getPassword()))
            throw new ExceptionApp(400, "Не правильный старый пароль");
        user.setPassword(passwordEncoder.encode(header[2]));
        userRepository.save(user);
        new ObjectMapper().writeValue(response.getOutputStream(), true);
    }

    public String[] headerDecode64(HttpServletRequest request) {
        String authentication = request.getHeader(HttpHeaders.AUTHORIZATION);
        String basic = authentication.substring(6);
        String pair = new String(Base64.decodeBase64(basic));
        return pair.split(":");
    }


}
