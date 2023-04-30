package shop.yeeerim.yeeerimblog.dto.reply;

import lombok.*;
import shop.yeeerim.yeeerimblog.model.reply.Reply;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ReplyResponseDTO {
	private String content;
	private String writer;
	private Long boardId;
	private Long id;
	public ReplyResponseDTO (Reply reply){
		this.content= reply.getContent();
		this.writer=reply.getUser().getUsername();
		this.boardId=reply.getBoard().getId();
		this.id=reply.getId();
	}
}
