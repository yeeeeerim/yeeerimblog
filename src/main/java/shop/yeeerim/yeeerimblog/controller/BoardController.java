package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;
import shop.yeeerim.yeeerimblog.dto.board.BoardRequest;
import shop.yeeerim.yeeerimblog.model.board.Board;
import shop.yeeerim.yeeerimblog.service.BoardService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;

	//RestAPI 주소설계 규칙에서는 자원에 복수를 붙인다. boards 정석 !
	@GetMapping({"/","board"})
	public String main(@RequestParam(defaultValue = "0") Integer page, Model model){

		Page<Board> boardPG=boardService.글목록보기(page);
		model.addAttribute("boardPG",boardPG);
		return "board/main";
	}

	@GetMapping("/s/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	@PostMapping("/s/board/save")
	public String save(BoardRequest.SaveInDTO saveInDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
		boardService.글쓰기(saveInDTO,myUserDetails.getUser().getId());
		return "redirect:/";
	}

	@GetMapping( "/board/{id}")
	public String detail(@PathVariable Long id, Model model){
		Board board = boardService.게시글상세보기(id);
		model.addAttribute("board", board);
		return "board/detail";
	}


}
