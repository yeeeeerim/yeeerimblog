package shop.yeeerim.yeeerimblog.model.reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply,Long> {
	@Query("select r from Reply r join fetch r.board where r.board.id = :boardId")
	List<Reply> findByBoardId(@Param("boardId")Long boardId);
}
