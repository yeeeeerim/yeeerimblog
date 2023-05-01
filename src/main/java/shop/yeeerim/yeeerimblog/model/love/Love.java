package shop.yeeerim.yeeerimblog.model.love;

import lombok.*;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.user.User;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "love_tb")
@Entity
public class Love {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="board_id")
	private Board board;
}
