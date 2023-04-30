package shop.yeeerim.yeeerimblog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.yeeerim.yeeerimblog.dto.reply.ReplyRequestDTO;
import shop.yeeerim.yeeerimblog.dto.reply.ReplyResponseDTO;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.model.board.BoardRepository;
import shop.yeeerim.yeeerimblog.model.reply.Reply;
import shop.yeeerim.yeeerimblog.model.reply.ReplyRepository;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
	private final BoardRepository boardRepository;
	private final ReplyRepository replyRepository;
	private final UserRepository userRepository;

	@Transactional
	public void saveReply(ReplyRequestDTO replyRequestDTO,Long userId){
		try {
			User userPS = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다. "));
			Board boardPS = boardRepository.findById(replyRequestDTO.getBoardId()).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다."));
			replyRepository.save(replyRequestDTO.toEntity(userPS,boardPS));
		}catch(Exception e){
			throw new RuntimeException("댓글쓰기 실패 : "+e.getMessage());
		}
	}
	@Transactional(readOnly = true)
	public List<ReplyResponseDTO> replyList(Long boardId){
		try{
			boardRepository.findById(boardId).orElseThrow(()->new RuntimeException("게시글을 찾을 수 없습니다."));
			List<Reply> replyList = replyRepository.findByBoardId(boardId);
			List<ReplyResponseDTO>dtoList = new ArrayList<>();

			for(Reply reply : replyList){
				ReplyResponseDTO dto = new ReplyResponseDTO(reply);
				dtoList.add(dto);

			}

			return dtoList;
		}catch (Exception e){
			throw new RuntimeException("댓글 불러오기 실패"+e.getMessage());
		}
	}





}
