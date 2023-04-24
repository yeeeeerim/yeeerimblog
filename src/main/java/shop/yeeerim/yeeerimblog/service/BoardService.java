package shop.yeeerim.yeeerimblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeeerim.yeeerimblog.dto.board.BoardRequest;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.board.BoardQueryRepository;
import shop.yeeerim.yeeerimblog.model.board.BoardRepository;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final BoardQueryRepository boardQueryRepository;
	@Transactional
	public void 글쓰기(BoardRequest.SaveInDTO saveInDTO,Long userId){
		try{
			User userPS= userRepository.findById(userId).orElseThrow(
					()-> new RuntimeException("유저를 찾을 수 없습니다. ")
			);

			boardRepository.save(saveInDTO.toEntity(userPS));
		} catch(Exception e){
			throw new RuntimeException("글쓰기 실패 : "+e.getMessage());
		}


	}

	@Transactional(readOnly = true) //변경 감지하지마 고립성(repeatable read)
	public Page<Board> 글목록보기(int page) { //CSR은 DTO로 변경해서 돌려줘야함
		// 1. 모든 전략은 Lazy : 이유는 필요할때만 가져오려고
		// 2. 필요할때는 직접 fetch join을 사용해라
		return boardQueryRepository.findAll(page);
	}
}
