package com.dsa.web6.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.dsa.web6.dto.MemberDTO;
import com.dsa.web6.security.AuthenticatedUser;
import com.dsa.web6.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	private String saveFile(MultipartFile file) {
	    try {
	        // 파일 저장 경로 지정
	        String uploadDir = System.getProperty("java.io.tmpdir") + "uploads/";
	        Files.createDirectories(Paths.get(uploadDir));
	        Path path = Paths.get(uploadDir + file.getOriginalFilename());
	        Files.copy(file.getInputStream(), path);
	        return path.toString(); // 저장된 파일 경로 반환
	    } catch (Exception e) {
	        log.error("파일 저장 중 오류 발생: {}", e.getMessage());
	        throw new RuntimeException("파일 저장 중 오류 발생: " + e.getMessage());
	    }
	}
	
	@GetMapping("login")
	public String login() {
		return "member/login";
	}
	
	@GetMapping("join")
	public String join() {
		return "member/join";
	}
	
	@PostMapping("join")
    public String join(@ModelAttribute MemberDTO member) {
        log.debug("전달된 가입정보 : ", member);

        memberService.join(member);
        return "member/login";
    }
	
	
	
	@GetMapping("info")
	public String info(Model model) {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    
	    if (authentication != null && authentication.isAuthenticated()) {
	        String memberId = authentication.getName(); // 로그인된 사용자 ID
	        
	        // 회원 정보 조회
	        MemberDTO member = memberService.findById(memberId); // 서비스에서 ID로 회원 정보를 조회
	        model.addAttribute("member", member);
	    }

	    return "member/info";
	}
	
	@PostMapping("info")
	public String updateInfo(@AuthenticationPrincipal AuthenticatedUser user,
	                         @ModelAttribute MemberDTO memberDTO,
	                         @RequestParam(value = "file", required = false) MultipartFile file) {
	    // 사용자 ID를 memberDTO에 설정
	    memberDTO.setMemberId(user.getUsername());

	    // 프로필 사진 저장 로직
	    if (file != null && !file.isEmpty()) {
	        String profileImagePath = saveFile(file); // 파일 저장 메서드 호출
	        memberDTO.setProfileImage(profileImagePath); // 프로필 이미지 경로 설정
	        log.debug("저장된 프로필 이미지 경로: {}", profileImagePath);
	    }

	    // 회원 정보 수정 처리
	    memberService.update(memberDTO);
	    // 수정 후 메인 페이지로 리다이렉트
	    return "redirect:/"; // 수정 후 이동할 페이지를 설정
	}

	
}
