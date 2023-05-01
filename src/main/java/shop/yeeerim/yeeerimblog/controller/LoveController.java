package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception404;
import shop.yeeerim.yeeerimblog.dto.love.LoveRequestDTO;
import shop.yeeerim.yeeerimblog.service.LoveService;

@RestController
@RequiredArgsConstructor
public class LoveController {
	private final LoveService loveService;

	@PostMapping("/s/love/save")
	public void addLove(@RequestBody LoveRequestDTO loveRequestDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
		if(loveRequestDTO.getUserId()==myUserDetails.getUser().getId()){
			loveService.saveLove(loveRequestDTO);
		}else{
			throw new Exception404("잘못된 요청입니다. ");
		}
	}

	@PostMapping("/s/love/delete")
	public void deleteLove(@RequestBody LoveRequestDTO loveRequestDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
		if(loveRequestDTO.getUserId()==myUserDetails.getUser().getId()){
			loveService.deleteLove(loveRequestDTO);
		}else{
			throw new Exception404("잘못된 요청입니다. ");
		}
	}


}
