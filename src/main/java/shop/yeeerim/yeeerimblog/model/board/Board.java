package shop.yeeerim.yeeerimblog.model.board;

import lombok.*;
import shop.yeeerim.yeeerimblog.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "board_tb")
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	private String title;
	@Lob //4GB
	private String content;
	@Lob //4GB
	private String thumbnail; //content에 등록된 사진 중 하나를 선택해서 자동으로 만들기
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public void updateBoard(String title, String content, String thumbnail){
		this.title=title;
		this.content=content;
		this.thumbnail=thumbnail;
	}

}