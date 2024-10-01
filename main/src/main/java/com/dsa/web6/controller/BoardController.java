package com.dsa.web6.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsa.web6.dto.MemberDTO;
import com.dsa.web6.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("board")
@RequiredArgsConstructor
public class BoardController {

	private final MemberService memberService;
	
	@GetMapping("mylist")
	public String mylist(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && authentication.isAuthenticated()) {
	        String memberId = authentication.getName(); // 로그인된 사용자 ID
	        
	        // 회원 정보 조회
	        MemberDTO member = memberService.findById(memberId); // 서비스에서 ID로 회원 정보를 조회
	        model.addAttribute("member", member);
	    }

	    return "board/mylist";
	}
	
	
}
