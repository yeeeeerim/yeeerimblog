package shop.yeeerim.yeeerimblog.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception400;
import shop.yeeerim.yeeerimblog.core.exception.ssr.Exception500;
import shop.yeeerim.yeeerimblog.core.util.MyFileUtil;
import shop.yeeerim.yeeerimblog.dto.user.UserRequest;
import shop.yeeerim.yeeerimblog.model.user.User;
import shop.yeeerim.yeeerimblog.model.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	@Value("${file.path}")
	private String uploadFolder;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder; //시큐리티에서 Bean등록함

	//insert, update, delete ->try-catch 처리
	@Transactional
	public void 회원가입(UserRequest.JoinInDTO joinInDTO){
		try{
			// 1. 패스워드 암호화
			joinInDTO.setPassword(passwordEncoder.encode(joinInDTO.getPassword()));
			// 2.DB 저장
			userRepository.save(joinInDTO.toEntity());
		}catch (Exception e){
			throw new RuntimeException("회원가입 오류 : "+e.getMessage());
		}

	} // 더티체킹, DB 세션종료

	public User 회원프로필보기(Long id) {
		User userPS = userRepository.findById(id)
				.orElseThrow(()->new Exception400("id", "해당 유저가 존재하지 않습니다"));
		return userPS;
	}

	@Transactional
	public User 프로필사진수정(MultipartFile profile, Long id) {
		try {
			String uuidImageName = MyFileUtil.write(uploadFolder, profile);
			User userPS = userRepository.findById(id)
					.orElseThrow(()->new Exception500("로그인 된 유저가 DB에 존재하지 않음"));
			userPS.changeProfile(uuidImageName);
			return userPS;
		}catch (Exception e){
			throw new Exception500("프로필 사진 등록 실패 : "+e.getMessage());
		}
	}
}
