package shop.yeeerim.yeeerimblog.model.reply;

import lombok.*;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.user.User;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "reply_tb")
@Entity
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	private String content;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="board_id")
	private Board board;

}
