package shop.yeeerim.yeeerimblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;
import shop.yeeerim.yeeerimblog.dto.reply.ReplyRequestDTO;
import shop.yeeerim.yeeerimblog.service.ReplyService;


@Controller
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;

	@PostMapping("/s/reply/save")
	public String saveReply(ReplyRequestDTO replyRequestDTO, @AuthenticationPrincipal MyUserDetails myUserDetails){
		replyService.saveReply(replyRequestDTO, myUserDetails.getUser().getId());
		return "redirect:/board/"+replyRequestDTO.getBoardId().toString();
	}
}
