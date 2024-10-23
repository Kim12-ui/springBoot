package com.dsa.tabidabi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsa.tabidabi.domain.dto.MemberDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityListDTO;
import com.dsa.tabidabi.security.AuthenticatedUser;
import com.dsa.tabidabi.service.MemberService;
import com.dsa.tabidabi.service.home.HomeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

	// application.properties 파일의 게시판 관련 설정값
	// import org.springframework.beans.factory.annotation.Value; 실행
	@Value("${community.uploadPath}")
	String uploadPath;
	
	@Value("${community.pageSize}")
	int pageSize;		// 페이지당 글 수
	
	@Value("${community.linkSize}")
	int linkSize;		// 페이지 이동 링크 수, 페이지 번호 링크의 개수
	
	private final HomeService hs;
	private final MemberService memberService;
	
	/**
	 * 인기 패키지
	 * @param model
	 * @return home.html
	 */
	@GetMapping({"","/"})
	public String home() {
		return "home";
	}
	
	/**
	 * 페이징 처리로 인기 게시판 구현
	 * @param page
	 * @param continent
	 * @param country
	 * @param title
	 * @return home.html
	 */
	@ResponseBody
	@PostMapping("popularPostSearch")
	public ResponseEntity<?> popularPostSearch(
			// 페이징 관련된 변수들
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "continent", defaultValue = "") String continent,
			@RequestParam(value = "country", defaultValue = "") String country,
			@RequestParam(value = "title", defaultValue = "") String title
			) {
		log.debug("선택한 대륙 : {}",continent);
		log.debug("선택한 나라 : {}",country);
		log.debug("검색내용 : {}",title);
		log.debug("페이지 : {}",page);
		
		Page<CommunityListDTO> communityListDTOs = hs.getPopularSearchResult(continent,country,title,page,pageSize);
		
		for (CommunityListDTO dto : communityListDTOs) {
			MemberDTO memberDTO = memberService.findById(dto.getCommunityDTO().getMemberId());
			dto.setMemberDTO(memberDTO);
		}
		
		Map<String, Object> response = new HashMap<>();
	    response.put("communityListDTOs", communityListDTOs);
		
		// 로그인 X시 => AuthenticatedUser user == null
		// 로그인 O시 => AuthenticatedUser user != null
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    AuthenticatedUser user = (authentication != null 
	    		&& authentication.isAuthenticated() 
	    		&& !(authentication instanceof AnonymousAuthenticationToken))
	            ? (AuthenticatedUser) authentication.getPrincipal()
	            : null;
	    if (user != null) {
	    	String memberId = user.getId();
	    	
	    	// 해당 인증된 아이디로 눌렀던 커뮤니티 리스트 목록 불러오기
	    	List<CommunityDTO> selectedLikedList = hs.selectLikedList(memberId);
	    	
	    	// 정보 불러오기 (해당 인증된 아이디로 눌렀던 커뮤니티 리스트 목록)
	    	for (CommunityDTO dto : selectedLikedList) {
	    		System.out.println(dto.getCommunityId());
	    	}	
	    	// selectedLikedList 프론트엔드로 보내기
	    	response.put("selectedLikedList", selectedLikedList);
	    } else {
	    	System.out.println("로그인하지 않았습니다.");
	    	List<CommunityDTO> selectedLikedList = new ArrayList<>();
	    	CommunityDTO dto = CommunityDTO.builder().communityId(0).build();
	    	selectedLikedList.add(dto);
	    	response.put("selectedLikedList", selectedLikedList);
	    }
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 좋아요 기능 구현
	 */
	@ResponseBody
	@PostMapping("like")
	public ResponseEntity<?> like(
			@RequestParam("communityId") Integer communityId,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		// true : 좋아요 생성 (community_like에 정보 추가, insert)
		// false : 좋아요 삭제 (community_like에 정보 삭제, delete)
		Boolean likeResult = hs.like(communityId, user);
		
		return ResponseEntity.ok(likeResult);
	}
}
