package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BoardController {

	//RestAPI 주소설계 규칙에서는 자원에 복수를 붙인다. boards 정석 !
	@GetMapping({"/","board"})
	public String main(){
		return "board/main";
	}

	@GetMapping("/s/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
