package shop.yeeerim.yeeerimblog.dto.user;

import lombok.Getter;
import lombok.Setter;
import shop.yeeerim.yeeerimblog.model.user.User;

import javax.validation.constraints.NotEmpty;

public class UserRequest {
	@Getter @Setter
	public static class JoinInDTO{
		@NotEmpty
		private String username;
		@NotEmpty
		private String password;
		@NotEmpty
		private String email;

		public User toEntity(){
			return User.builder().
					username(username)
					.password(password)
					.email(email)
					.role("USER")
					.status(true)
					.profile("person.png")
					.build();
		}

	}
}
