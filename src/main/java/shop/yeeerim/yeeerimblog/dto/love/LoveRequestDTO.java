package shop.yeeerim.yeeerimblog.dto.love;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.love.Love;
import shop.yeeerim.yeeerimblog.model.user.User;

@Getter @Setter @ToString
public class LoveRequestDTO {
	private Long userId;
	private Long boardId;

	public Love toEntity(User user, Board board){
		return Love.builder().user(user).board(board).build();
	}
}
