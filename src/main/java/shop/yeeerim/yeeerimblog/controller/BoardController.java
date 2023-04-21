package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;
import shop.yeeerim.yeeerimblog.dto.board.BoardRequest;
import shop.yeeerim.yeeerimblog.service.BoardService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;

	//RestAPI 주소설계 규칙에서는 자원에 복수를 붙인다. boards 정석 !
	@GetMapping({"/","board"})
	public String main(){
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
}
