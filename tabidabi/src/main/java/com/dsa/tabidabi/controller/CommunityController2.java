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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityInfoDetailsDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityListDTO;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;
import com.dsa.tabidabi.repository.community.CommunityRepository;
import com.dsa.tabidabi.security.AuthenticatedUser;
import com.dsa.tabidabi.service.CommunityService2;
import com.dsa.tabidabi.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController2 {
	
	@Value("${community.pageSize}")
	int pageSize;		// 페이지당 글 수
	
	@Value("${community.linkSize}")
	int linkSize;		// 페이지 이동 링크 수, 페이지 번호 링크의 개수
	
    private final CommunityService2 communityService2;
    private final MemberService memberService;
    
    private final CommunityRepository cr;

    /**
	 * 커뮤니티 리스트로 이동
	 * @return list.html
	 */
	@GetMapping("list")
	public String list() {
		return "community/list";
	}
	
	@ResponseBody
	@PostMapping("getCommunityList")
	public ResponseEntity<?> list(
			@RequestParam(value="continent", defaultValue = "") String continent,
			@RequestParam(value="country", defaultValue = "") String country,
			@RequestParam(value="title", defaultValue = "") String title,
			
			// 페이징 처리
			@RequestParam(value = "page", defaultValue = "1") int page
			) {
		log.debug("선택한 대륙 : {}",continent);
		log.debug("선택한 나라 : {}",country);
		log.debug("검색내용 : {}",title);
		
		Page<CommunityListDTO> list = communityService2.getSearchResult(continent,country,title,page,pageSize);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("list", list);
		
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
			List<CommunityDTO> selectedLikedList = communityService2.selectLikedList(memberId);
			
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
	 * 좋아요 누르기 기능
	 * @param communityId
	 * @param user
	 * @return likeResult = true (좋아요 +1), likeResult = false (좋아요 -1)
	 */
	@ResponseBody
	@PostMapping("like")
	public ResponseEntity<?> like(
			@RequestParam(value="communityId") Integer communityId,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		log.debug("좋아요 누른 게시판 번호 : {}", communityId);
		log.debug("현재 로그인한 유저 : {}", user);
		
		Boolean likeResult = communityService2.like(communityId, user);
		
		return ResponseEntity.ok(likeResult);
	} 
	
	/**
	 * 커뮤니티 상세 읽기
	 * @param communityId 해당 게시물의 아이디
	 * @return read.html
	 */
	@GetMapping("/read")
	public String read(@RequestParam("communityId") Integer communityId, Model model) {
		System.out.println("communityId: {}"+communityId);
		 
		  List<CommunityInfoDetailsDTO> posts = communityService2.getPostsBycommunityId(communityId);
		  
		  CommunityEntity entity = communityService2.findById(communityId);
		  
		  // 해당 게시판 들어갈때마다 조회수 +1증가
		  entity.setViewCount(entity.getViewCount()+1);
		  
		  cr.save(entity);
		  
		  for(CommunityInfoDetailsDTO post:posts) {
			  System.out.println(post);
		  }
		
	    if (posts != null) {
	    	System.out.println("x");
	        model.addAttribute("posts", posts);
	        model.addAttribute("entity",entity);
	        } else {System.out.println("y");
	        model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
	    }
	    return "community/read";
	}
	
}
