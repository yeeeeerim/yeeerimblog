package shop.yeeerim.yeeerimblog.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.BCException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import shop.yeeerim.yeeerimblog.core.auth.MyUserDetails;

import javax.servlet.http.HttpSession;

@Slf4j
@Configuration
public class SecurityConfig {

	//password 인코딩
	@Bean
	BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	//권한주소 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

		//1. CSRF 해제
		http.csrf().disable();

		//2. frame option 해제 (시큐리티 h2-console 접속 허용을 위해)
		http.headers().frameOptions().disable();

		//3.Form 로그인 설정
		http.formLogin().loginPage("/loginForm")
				.loginProcessingUrl("/login")
				.successHandler((request, response, authentication) -> {
					log.debug("디버그 : 로그인 성공");

					MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
					HttpSession session = request.getSession();
					session.setAttribute("sessionUser",myUserDetails.getUser());

					response.sendRedirect("/");
				})
				.failureHandler((request, response, exception) -> {
					log.debug("디버그 : 로그인 실패: "+ exception.getMessage());
					response.sendRedirect("/loginForm");
				});

		//4. 인증 권한 필터 설정 (s로 시작하는 주소로 들어오면 무조건 인증처리 해야함)
		http.authorizeRequests(
				authorize ->authorize.antMatchers("/s/**").authenticated().anyRequest().permitAll()
		);


		return http.build();
	}
}
