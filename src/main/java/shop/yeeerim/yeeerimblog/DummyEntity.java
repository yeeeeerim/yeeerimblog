package shop.yeeerim.yeeerimblog;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.user.User;

public class DummyEntity {

	protected User newUser(String username, BCryptPasswordEncoder passwordEncoder){
		return User.builder()
				.username(username)
				.password(passwordEncoder.encode("1234"))
				.email(username+"@nate.com")
				.role("USER")
				.profile("person.png")
				.build();
	}

	protected Board newBoard(String title, User user){
		return Board.builder()
				.title(title)
				.content(title+"에 대한 내용입니다")
				.user(user)
				.thumbnail("/images/dora.png")
				.build();
	}
}