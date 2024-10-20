package com.dsa.tabidabi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * 시큐리티 환경설정
 */
// spring의 설정 클래스임을 나타냄
@Configuration
// Security 활성화
@EnableWebSecurity
public class WebSecurityConfig {
	//설정파일이 없다면 인증 방식은 폼 로그인 방식과 httpBasic로그인 방식을 제공
	
    //로그인 없이 접근 가능 경로
    private static final String[] PUBLIC_URLS = {
    		"/"                     //root
    		,"/representativeimage/**"
            , "/images/**"          //이미지 경로
            , "/css/**"             //CSS파일들
            , "/js/**"              //JavaSCript 파일들
            , "/member/join"        //회원가입
            , "/member/idDuplicate" // 아이디 중복체크
            , "/member/info"        //개인정보 수정
            , "/community/list"		//커뮤니티 리스트
            , "/community/read"		//커뮤니티 글 상세읽기
            , "/community/replyList"
            , "/popularPostSearch"  //홈페이지 인기 게시판
            , "/community/getCommunityList"	//커뮤니티 게시판
    };

    /**
     * 오류날시
     * CustomLoginSuccessHandler customLoginSuccessHandler 해당 파라미터 삭제
     *  .defaultSuccessUrl("/", true) 부분 주석 없애기
     *  successHandler(customLoginSuccessHandler) 삭제
     */
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http
            //요청에 대한 권한 설정
            .authorizeHttpRequests(author -> author
                .requestMatchers(PUBLIC_URLS).permitAll()   //모두 접근 허용
                .anyRequest().authenticated()               //그 외의 모든 요청은 인증 필요
            )
            //HTTP Basic 인증을 사용하도록 설정
            .httpBasic(Customizer.withDefaults())
            //폼 로그인 설정
            .formLogin(formLogin -> formLogin
                    .loginPage("/member/login")         		//로그인폼 페이지 경로
                    .usernameParameter("id")                	//폼의 ID 파라미터 이름
                    .passwordParameter("password")          	//폼의 비밀번호 파라미터 이름
					.loginProcessingUrl("/member/login")    	//로그인폼 제출하여 처리할 경로
                    .defaultSuccessUrl("/", true)           	//로그인 성공 시 이동할 경로
                    .permitAll()                            	//로그인 페이지는 모두 접근 허용
            )
            //로그아웃 설정
            .logout(logout -> logout
                    .logoutUrl("/member/logout")            //로그아웃 처리 경로
                    .logoutSuccessUrl("/")                  //로그아웃 성공 시 이동할 경로
            );

        http
            .cors(AbstractHttpConfigurer::disable)			// CORS (Cross-Origin Resource Sharing) 설정 
            .csrf(AbstractHttpConfigurer::disable);			// CSRF (Cross-Site Request Forgery) 보호 비활성화

        return http.build();
    }

    //비밀번호 암호화를 위한 인코더를 빈으로 등록
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
