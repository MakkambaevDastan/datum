package datum;

import datum.auth.AuthenticationService;
import datum.auth.RegisterRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static datum.user.Role.*;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationService service
	) {
		return args -> {
			var admin = RegisterRequest.builder()
					.firstname("Makkambaev")
					.surname("Dastan")
					.patronymic("Talantbekovich")
					.email("makkambaevdastan@gmail.com")
					.birthDay("2020-12-21")
					.phone("+996777112533")
					.username("MakkambaevDastan")
					.password("password")
					.build();
			System.out.println("MakkambaevDastan token: " + service.register(admin).getAccessToken());

			var manager = RegisterRequest.builder()
					.firstname("Ababakirov")
					.surname("Jumabek")
					.patronymic("Mamyrgazovich")
					.username("AbabakirovJumabek")
					.email("user@mail.com")
					.birthDay("2020-12-30")
					.phone("+996778585999")
					.password("password")
					.build();
			System.out.println("AbabakirovJumabek token: " + service.register(manager).getAccessToken());

		};
	}
}
