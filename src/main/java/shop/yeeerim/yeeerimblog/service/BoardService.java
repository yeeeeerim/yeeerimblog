package shop.yeeerim.yeeerimblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeeerim.yeeerimblog.dto.board.BoardRequest;
import shop.yeeerim.yeeerimblog.model.board.BoardRepository;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;
	private final UserRepository userRepository;
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
}
