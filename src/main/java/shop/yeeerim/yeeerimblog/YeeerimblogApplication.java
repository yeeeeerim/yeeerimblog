package shop.yeeerim.yeeerimblog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

@SpringBootApplication
public class YeeerimblogApplication {

	@Bean
	CommandLineRunner init(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
		return args -> {
			User ssar = User.builder()
					.username("ssar")
					.password(passwordEncoder.encode("1234"))
					.email("ssar@nate.com")
					.role("USER")
					.profile("person.png")
					.status(true)
					.build();
			userRepository.save(ssar);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(YeeerimblogApplication.class, args);
	}

}
