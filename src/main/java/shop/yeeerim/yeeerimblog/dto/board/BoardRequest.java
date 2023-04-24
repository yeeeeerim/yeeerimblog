package shop.yeeerim.yeeerimblog.dto.board;

import lombok.Getter;
import lombok.Setter;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.user.User;

public class BoardRequest {
	@Getter @Setter
	public static class SaveInDTO{
		private String title;
		private String content;

		public Board toEntity(User user,String thumbnail){
			return Board.builder()
					.user(user)
					.title(this.title)
					.content(this.content)
					.thumbnail(thumbnail)
					.build();
		}
	}
}
