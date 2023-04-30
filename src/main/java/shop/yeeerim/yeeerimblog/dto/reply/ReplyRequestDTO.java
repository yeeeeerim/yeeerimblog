package shop.yeeerim.yeeerimblog.dto.reply;

import lombok.Getter;
import lombok.Setter;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.reply.Reply;
import shop.yeeerim.yeeerimblog.model.user.User;
@Getter
@Setter
public class ReplyRequestDTO {
	private Long boardId;
	private String content;

	public Reply toEntity(User user, Board board){
		return Reply.builder().
				user(user).
				board(board).
				content(this.content).
				build();
	}
}
