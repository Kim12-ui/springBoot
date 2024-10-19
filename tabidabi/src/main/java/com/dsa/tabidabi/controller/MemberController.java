package com.dsa.tabidabi.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.tabidabi.domain.dto.MemberDTO;
import com.dsa.tabidabi.security.AuthenticatedUser;
import com.dsa.tabidabi.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	/**
	 * 요청시 회원가입 페이지로 이동
	 * @return joinForm.html
	 */
	@GetMapping("login")
	public String login() {
		return "member/loginForm";
	}
	
	/**
	 * 요청시 회원가입 페이지로 이동
	 * @return joinForm.html
	 */
	@GetMapping("join")
	public String join() {
		return "member/joinForm";
	}
	
	/**
	 * 아이디 존재 여부 확인
	 * @param id 조회할 아이디
	 * @return result true/false 아이디가 존재하면 true, 존재하지 않으면 false
	 */
	@ResponseBody
	@PostMapping("idDuplicate")
	public Boolean idCheck(@RequestParam("id") String id) {
		log.debug("조회할 아이디 : {}",id);
		
		boolean result = memberService.idDuplicate(id);
		log.debug("결과: {}",result);
		
		return result;
	}
	
	/**
	 * 회원가입 정보를 받아 회원가입 처리
	 * @param member
	 * @return loginForm.html
	 */
	@PostMapping("join")
	public String join(@ModelAttribute MemberDTO member) {
		log.debug("전달된 회원가입정보 : {}",member);
		memberService.join(member);
		return "member/loginForm";
	}
	
	
}