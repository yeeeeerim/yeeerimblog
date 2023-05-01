package shop.yeeerim.yeeerimblog.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "user_tb")
@Entity
@ToString
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, length = 20)
	private String username;
	@Column(length = 60)
	private String password; //20자 이하만 받을 예정
	@Column(length = 50)
	private String email;
	private String role; // USER(고객)
	private String profile; // User profile 사진의 경로
	private Boolean status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public void changeProfile(String profile){
		this.profile = profile;
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
	//회원정보 수정
	public void update(String password, String email) {
		this.password = password;
		this.email = email;
	}
}