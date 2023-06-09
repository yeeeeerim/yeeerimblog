package shop.yeeerim.yeeerimblog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.board.BoardRepository;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class YeeerimblogApplication  extends DummyEntity{

	@Profile("dev")
	@Bean
	CommandLineRunner init(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository, BoardRepository boardRepository) {
		return args -> {
			User ssar = newUser("ssar", passwordEncoder);
			User cos = newUser("cos", passwordEncoder);
			List<Board> boardList = new ArrayList<>();
			for (int i = 1; i < 11; i++) {
				boardList.add(newBoard("제목"+i, ssar));
			}
			for (int i = 11; i < 21; i++) {
				boardList.add(newBoard("제목"+i, cos));
			}
			userRepository.saveAll(Arrays.asList(ssar, cos));
			boardRepository.saveAll(boardList);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(YeeerimblogApplication.class, args);
	}

}
