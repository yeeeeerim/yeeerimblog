package shop.yeeerim.yeeerimblog.model.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
	@Query("select  b from Board b where b.user=:userId")
	List<Board> findByUserId(@Param("userId")Long userId);

	@Query("select b from Board b join fetch b.user")
	List<Board> findAllFetchUser();

	@Query("select distinct b.user.id from Board b")
	List<Long> findUserIdDistinct(); // 1,2,3

	@Query("select b from Board b where b.user.id in :userIds")
	List<Board> findByUserIds(@Param("userIds") List<Long> userIds);

	@Query("select b from Board b join fetch b.user where b.id = :id")
	Optional<Board> findByIdFetchUser(@Param("id") Long id);


}
