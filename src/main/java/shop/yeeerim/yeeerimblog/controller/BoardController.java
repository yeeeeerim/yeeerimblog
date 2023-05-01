package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception403;
import shop.yeeerim.yeeerimblog.dto.board.BoardRequestDTO;
import shop.yeeerim.yeeerimblog.dto.reply.ReplyResponseDTO;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.service.BoardService;
import shop.yeeerim.yeeerimblog.service.LoveService;
import shop.yeeerim.yeeerimblog.service.ReplyService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;
	private final ReplyService replyService;
	private final LoveService loveService;

	//RestAPI 주소설계 규칙에서는 자원에 복수를 붙인다. boards 정석 !
	@GetMapping({"/","board"})
	public String main(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = " ") String keyword, Model model){

		Page<Board> boardPG=boardService.글목록보기(page, keyword);
		model.addAttribute("boardPG",boardPG);
		model.addAttribute("keyword",keyword);
		return "board/main";
	}

	@GetMapping("/s/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	@PostMapping("/s/board/save")
	public String save(BoardRequestDTO.SaveInDTO saveInDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
		boardService.글쓰기(saveInDTO,myUserDetails.getUser().getId());
		return "redirect:/";
	}

	@GetMapping( "/board/{id}")
	public String detail(@PathVariable Long id, Model model,@AuthenticationPrincipal MyUserDetails myUserDetails){

		boolean like = false;
		if(myUserDetails!=null){
			Long userId=myUserDetails.getUser().getId();
			like = loveService.findLove(id,userId);
		}
		Board board = boardService.게시글상세보기(id);
		List<ReplyResponseDTO> replyList=replyService.replyList(id);
		Long loveCount=loveService.countLove(id);
		model.addAttribute("board", board);
		model.addAttribute("reply",replyList);
		model.addAttribute("love",like);
		model.addAttribute("loveCount",loveCount);
		return "board/detail";
	}

	@PostMapping("/s/board/{id}/delete")
	public String delete(@PathVariable Long id, @AuthenticationPrincipal MyUserDetails myUserDetails) {
		boardService.게시글삭제(id, myUserDetails.getUser().getId());
		return "redirect:/";
	}
	@GetMapping("/s/board/{id}/updateForm")
	public String updateForm(@PathVariable Long id, Model model,@AuthenticationPrincipal MyUserDetails myUserDetails) {
		Board board = boardService.게시글상세보기(id);
		if(board.getUser().getId()!=myUserDetails.getUser().getId()){
			throw new Exception403("수정권한이 없습니다");
		}
		model.addAttribute("board", board);
		return "board/updateForm";
	}

	@PostMapping("/s/board/{id}/update")
	public String update( @PathVariable Long id,BoardRequestDTO.UpdateInDTO updateInDTO,@AuthenticationPrincipal MyUserDetails myUserDetails){
		updateInDTO.setId(id);
		boardService.게시글수정(updateInDTO,myUserDetails.getUser().getId());
		return "redirect:/board/"+updateInDTO.getId();
	}


}
