package shop.yeeerim.yeeerimblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception400;

import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception403;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception500;
import shop.yeeerim.yeeerimblog.core.util.MyParseUtil;
import shop.yeeerim.yeeerimblog.dto.board.BoardRequestDTO;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.board.BoardQueryRepository;
import shop.yeeerim.yeeerimblog.model.board.BoardRepository;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
	private final BoardQueryRepository boardQueryRepository;

	@Transactional
	public void 글쓰기(BoardRequestDTO.SaveInDTO saveInDTO, Long userId){
		try{
			User userPS= userRepository.findById(userId).orElseThrow(
					()-> new RuntimeException("유저를 찾을 수 없습니다. ")
			);
			String thumbnail = MyParseUtil.getThumbnail(saveInDTO.getContent());
			boardRepository.save(saveInDTO.toEntity(userPS,thumbnail));

			//boardRepository.save(saveInDTO.toEntity(userPS));
		} catch(Exception e){
			throw new RuntimeException("글쓰기 실패 : "+e.getMessage());
		}


	}

	/*
			isEmpty() 메소드는 문자열의 길이가 0인지 검사합니다.
			isBlank() 메소드는 문자열이 비어있거나 공백 문자열(whitespace-only string)인지 검사합니다.
	*/
	@Transactional(readOnly = true) //변경 감지하지마 고립성(repeatable read)
	public Page<Board> 글목록보기(int page ,String keyword) { //CSR은 DTO로 변경해서 돌려줘야함
		// 1. 모든 전략은 Lazy : 이유는 필요할때만 가져오려고
		// 2. 필요할때는 직접 fetch join을 사용해라
		if(keyword.isBlank()){
			return boardQueryRepository.findAll(page);
		}else {
			Page<Board> boardPGPS = boardQueryRepository.findAllByKeyword(page, keyword);
			return boardPGPS;
		}

	}

	public Board 게시글상세보기(Long id) {
		Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
				()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
		);
		// 1. Lazy Loading 하는 것 보다 join fetch 하는 것이 좋다.
		// 2. 왜 Lazy를 쓰냐면, 쓸데 없는 조인 쿼리를 줄이기 위해서이다.
		// 3. 사실 @ManyToOne은 Eager 전략을 쓰는 것이 좋다.
		// boardPS.getUser().getUsername();
		return boardPS;
	}

	@Transactional
	public void 게시글삭제(Long id, Long userId) {
		try {
			Board boardPS = boardRepository.findByIdFetchUser(id).orElseThrow(
					()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
			);
			if(boardPS.getUser().getId() != userId){
				throw new Exception403("권한이 없습니다");
			}
			boardRepository.deleteById(id);
		}catch (Exception e){
			throw new Exception500("게시글 삭제 실패 : "+e.getMessage());
		}
	}

	@Transactional
	public void 게시글수정(BoardRequestDTO.UpdateInDTO updateInDTO,Long userId){
		try{
			Board boardPS = boardRepository.findByIdFetchUser(updateInDTO.getId()).orElseThrow(
					()-> new Exception400("id", "게시글 아이디를 찾을 수 없습니다")
			);
			if(boardPS.getUser().getId() != userId){
				throw new Exception403("권한이 없습니다");
			}
			String thumbnail = MyParseUtil.getThumbnail(updateInDTO.getContent());
			boardPS.updateBoard(updateInDTO.getTitle(), updateInDTO.getContent(),thumbnail);
			boardRepository.save(boardPS);
		}catch (Exception e){
			throw new Exception500("게시글 수정 실패 : "+e.getMessage());
		}
	}
}
