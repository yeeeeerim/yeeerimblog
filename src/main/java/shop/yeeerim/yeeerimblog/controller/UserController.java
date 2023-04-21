package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.yeeerim.yeeerimblog.dto.user.UserRequest;
import shop.yeeerim.yeeerimblog.service.UserService;

@Controller
@RequiredArgsConstructor //의존성 주입
public class UserController {
	private final UserService userService;

	// 인증이 되지 않은 상태에서 인증과 관련된 주소는 앞에 엔티티 적지말기
	// write(post) 행위 : 리소스/식별자 : save or delete or update
	// read (get) : 리소스/식별자
	@PostMapping("/join")
	public String join(UserRequest.JoinInDTO joinInDTO){ //x-www-form-urlencoded
		userService.회원가입(joinInDTO);
		return "redirect:/loginForm";
	}

	@GetMapping("/joinForm")
	public String joinForm(){
		return "user/joinForm";
	}

	@GetMapping("/loginForm")
	public String loginForm(){
		return "user/loginForm";
	}
}
