package shop.yeeerim.yeeerimblog.dto.board;

import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.user.User;

public class BoardRequest {
	public static class SaveInDTO{
		private String title;
		private String content;

		public Board toEntity(User user){
			return Board.builder()
					.user(user)
					.title(title)
					.content(content)
					.thumbnail(null)
					.build();
		}
	}
}
