package com.dsa.tabidabi.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.tabidabi.domain.dto.MemberDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.security.AuthenticatedUser;
import com.dsa.tabidabi.service.CommunityService2;
import com.dsa.tabidabi.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("myinfo")
@RequiredArgsConstructor
public class MyinfoController {


	private final MemberService memberService;
	private final CommunityService2 communityService2;

	private String saveFile(MultipartFile file) {
		try {
			String uploadDir = "c:/teamproject/representativeimage/"; // 변경된 경로
			Files.createDirectories(Paths.get(uploadDir)); // 디렉토리 생성
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename(); // 파일명에 타임스탬프 추가
			Path path = Paths.get(uploadDir + fileName); // 저장할 파일 경로
			Files.copy(file.getInputStream(), path); // 파일 저장
			return fileName; // 저장된 파일명 반환
		} catch (Exception e) {
			log.error("파일 저장 중 오류 발생: {}", e.getMessage()); // 오류 로그
			throw new RuntimeException("파일 저장 중 오류 발생: " + e.getMessage());
		}
	}

	@GetMapping("/profile/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) {
		Path file = Paths.get("C:/teamproject/representativeimage/").resolve(filename);
		Resource resource = new FileSystemResource(file);

		if (!resource.exists()) {
			log.error("File not found: {}", file.toString()); // 파일 경로 로그
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.contentType(MediaType.IMAGE_JPEG).body(resource);
	}

	@GetMapping("myinfo")
	public String myinfo(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String memberId = authentication.getName();

			MemberDTO member = memberService.findById(memberId);
			// 프로필 이미지 URL을 생성
			if (member.getProfileImage() != null) {
				String imageUrl = "/myinfo/profile/" + member.getProfileImage();
				model.addAttribute("profileImageUrl", imageUrl);
			}
			model.addAttribute("member", member);
		}

		return "myinfo/myinfo";
	}

	@PostMapping("myinfo") // POST 요청 처리
	public String updateInfo(@AuthenticationPrincipal AuthenticatedUser user, @ModelAttribute MemberDTO memberDTO,
			@RequestParam(value = "file", required = false) MultipartFile file) {

		memberDTO.setMemberId(user.getUsername());

		if (file != null && !file.isEmpty()) {
			String fileName = saveFile(file); // 파일 저장 메서드 호출
			memberDTO.setProfileImage(fileName); // DTO에 파일명 추가
		}

		log.debug("업데이트할 회원 정보: {}", memberDTO);
		memberService.update(memberDTO); // 프로필 이미지 포함된 DTO로 업데이트

		return "redirect:/";
	}

	@GetMapping("sharingroom_list") // GET 요청에 대한 핸들러 메서드
	public String sharingroom_list(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // 현재 인증 정보 가져오기

		if (authentication != null && authentication.isAuthenticated()) {
			String memberId = authentication.getName(); // 로그인된 사용자 ID 가져오기

			MemberDTO member = memberService.findById(memberId); // 서비스에서 ID로 회원 정보를 조회
			model.addAttribute("member", member); // 모델에 회원 정보 추가
		}

		return "myinfo/sharingroom_list"; // 개인 게시글 목록 뷰 반환
	}

	/**
     * 내 커뮤니티 리스트로 이동
     * @return community.html
     */
    @GetMapping("community")
    public String community() {
        
        return "myinfo/community"; // HTML 파일 이름
    }

    @GetMapping("/getCommunityList")
    @ResponseBody
    public List<CommunityDTO> getCommunityList(Principal principal) {
        String username = principal.getName(); // 로그인한 사용자의 이름 또는 ID
        log.debug("Requested Community List for Member ID: {}", username); // 추가: 요청한 사용자 ID 로그
        return communityService2.getCommunitiesByMember(username);
    }








	
	@GetMapping("ourtrip")
	public String ourtrip() {
		return "myinfo/ourtrip";
	}

	@GetMapping("ourtripinfo")
	public String ourtripinfo() {
		return "myinfo/ourtripinfo";
	}
}
