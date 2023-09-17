package datum;

import datum.user.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.TimeZone;
import java.util.regex.Pattern;

@SpringBootApplication
public class Main {
    public static boolean isDateBithDay(String s) {
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
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
//        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(UserRepository.class);
//
//        Test test = new Test(app.getBean(UserRepository.class));
//        test.test();
    }

//
//	@Bean
//	public CommandLineRunner commandLineRunner(
//			AuthenticationService service
//	) {
//		return args -> {
//			var admin = UserDTO.builder()
//					.firstname("Makkambaev")
//					.email("makkambaevdastan@gmail.com")
//					.birthDay("2020-12-21")
//					.password("das")
//					.build();
//			HttpServletRequest request =
//			service.register(request, admin);
//
////			System.out.println("makkambaevdastan@gmail.com token: " +
////					service.register(HttpServletRequest, admin).getAccessToken());
//
//			var manager = UserDTO.builder()
//					.firstname("Ababakirov")
//					.email("user@mail.com")
//					.birthDay("2020-12-30")
//					.address("str. Masaliev 51")
//					.phone("+996778585999")
//					.password("juma")
//					.build();
//			System.out.println("user@mail.com token: " +
//					service.register(manager).getAccessToken());
//
//		};
//	}
}
