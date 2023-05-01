package shop.yeeerim.yeeerimblog.model.love;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LoveRepository extends JpaRepository<Love,Long> {
	boolean existsByBoardIdAndUserId(@Param("boardId")Long boardId,@Param("userId")Long userId);
	Optional<Love> findByUserIdAndBoardId(Long userId, Long boardId);
	Long countLoveByBoard_Id(Long boardId);


}
