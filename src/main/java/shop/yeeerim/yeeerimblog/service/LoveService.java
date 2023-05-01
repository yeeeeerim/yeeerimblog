package shop.yeeerim.yeeerimblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.LookupOverride;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception404;
import shop.yeeerim.yeeerimblog.dto.love.LoveRequestDTO;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.board.BoardRepository;
import shop.yeeerim.yeeerimblog.model.love.Love;
import shop.yeeerim.yeeerimblog.model.love.LoveRepository;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoveService {

	private final LoveRepository loveRepository;
	private final UserRepository userRepository;
	private final BoardRepository boardRepository;

	public boolean findLove(Long boardId, Long userId){
		return loveRepository.existsByBoardIdAndUserId(boardId,userId);
	}

	public void saveLove(LoveRequestDTO loveRequestDTO){
		User user = userRepository.findById(loveRequestDTO.getUserId()).orElseThrow(()-> new RuntimeException("유저를 찾을 수 없습니다. "));
		Board board=boardRepository.findById(loveRequestDTO.getBoardId()).orElseThrow(()-> new RuntimeException("게사글을 찾을 수 없습니다. "));
		loveRepository.save(loveRequestDTO.toEntity(user,board));
	}

	public void deleteLove(LoveRequestDTO loveRequestDTO){
		Love love = loveRepository.findByUserIdAndBoardId(loveRequestDTO.getUserId(), loveRequestDTO.getBoardId()).orElseThrow(()-> new RuntimeException("좋아요를 찾을 수 없습니다. "));
		loveRepository.deleteById(love.getId());
	}

	public Long countLove(Long boardId){
		return loveRepository.countLoveByBoard_Id(boardId);
	}







}
