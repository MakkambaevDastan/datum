package datum;

//import datum.user.*;

import datum.authenticate.User;
import datum.config.exception.ExceptionApp;
import datum.config.exception.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import java.net.URI;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

import static org.springframework.web.servlet.function.RequestPredicates.GET;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
public class Main {
    public static final Random random = new Random();

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public static Long getUserId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
//        return Stream.of(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .map(Authentication::getPrincipal)
//                .map(User.class::cast)
//                .map(User::getId)
//                .findFirst()
//                .orElseThrow();
    }
    public static int parseInt(String string) {
        if (isInteger(string)) return Integer.parseInt(string);
        throw new ExceptionApp(400, Message.INVALID_ID);
    }
    public static boolean isInteger(String string) {
        if (string == null) return false;
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    public static long parseLong(String string) {
        if (isLong(string)) return Long.parseLong(string);
        throw new ExceptionApp(400, Message.INVALID_ID);
    }
    public static boolean isLong(String string) {
        if (string == null) return false;
        try {
            Long.parseLong(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static boolean isDateOfBirth(String s) {
        return s.matches("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])");
    }

    public static boolean isLocalTime(String s) {
        return s.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }

    public static boolean isValidEmail(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

}
