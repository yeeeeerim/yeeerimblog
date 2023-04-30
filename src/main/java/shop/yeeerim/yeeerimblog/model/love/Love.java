package shop.yeeerim.yeeerimblog.model.love;

import lombok.*;

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
}
