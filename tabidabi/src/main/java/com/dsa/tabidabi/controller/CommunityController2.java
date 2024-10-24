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

import com.dsa.tabidabi.domain.dto.MemberDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityInfoDetailsDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityListDTO;
import com.dsa.tabidabi.domain.dto.community.ReplyDTO;
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
		
		for (CommunityListDTO dto : list) {
			MemberDTO memberDTO = memberService.findById(dto.getCommunityDTO().getMemberId());
			dto.setMemberDTO(memberDTO);
		}
		
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
		  
		  MemberDTO memberDTO = memberService.findById(entity.getMember().getMemberId());
		  
		  // 다음, 이전 게시물 ID 가져오기
		  Integer nextId = communityService2.findNextCommunityId(communityId);
		  Integer prevId = communityService2.findPreviousCommunityId(communityId);
		  
		  // 로그인 X시 => AuthenticatedUser user == null
		  // 로그인 O시 => AuthenticatedUser user != null
		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	      AuthenticatedUser user = (authentication != null 
	    		&& authentication.isAuthenticated() 
	    		&& !(authentication instanceof AnonymousAuthenticationToken))
	            ? (AuthenticatedUser) authentication.getPrincipal()
	            : null;
	      Boolean likeResult = false;
	      if (user != null) {
	    	  likeResult = communityService2.selectlike(communityId, user);
	      } else {
	    	  likeResult = false;
	      }
		  
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
	        model.addAttribute("likeResult",likeResult);
	        model.addAttribute("prevId",prevId);
	        model.addAttribute("nextId",nextId);
	        model.addAttribute("memberDTO",memberDTO);
	        } else {System.out.println("y");
	        model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
	    }
	    return "community/read";
	}
	
	/**
	 * 리플 목록 추가
	 * @param communityId 해당 커뮤니티 번호
	 * @param replyContent 댓글 내용
	 * @param user 로그인한 유저
	 * @return 댓글 목록 +1증가
	 */
	@ResponseBody
	@PostMapping("replyWrite")
	public void replyWrite(
			@RequestParam("communityId") int communityId,
			@RequestParam("replyContent") String replyContent,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		String memberId = user.getId();
		communityService2.replyWrite(communityId,replyContent,memberId);
	}
	
	/**
	 * 리플 리스트 불러오기
	 * @param communiryId 해당 커뮤니티 번호
	 * @return list 댓글 리스트
	 * @return memberId 지금 접속한 유저 아이디
	 */
	@ResponseBody
	@GetMapping("replyList")
	public ResponseEntity<?> getReplyList(
			@RequestParam("communityId") int communityId
			) {
		// 로그인 X시 => AuthenticatedUser user == null
		// 로그인 O시 => AuthenticatedUser user != null
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    AuthenticatedUser user = (authentication != null 
	    		&& authentication.isAuthenticated() 
	    		&& !(authentication instanceof AnonymousAuthenticationToken))
	            ? (AuthenticatedUser) authentication.getPrincipal()
	            : null;
	    
	    String loginId = (user != null) ? user.getId() : null;
		
		List<ReplyDTO> list = communityService2.getReplyList(communityId);
		
		Map<String, Object> response = new HashMap<>();
	    response.put("list", list);
	    response.put("loginId",loginId);
	    
		return ResponseEntity.ok(response);
	}
	
	/**
	 * 리플 글 삭제하기
	 * @param communityReplyId 해당 댓글 고유번호
	 * @param communityId 커뮤니티 게시판 번호
	 * @param user 로그인한 유저
	 */
	@ResponseBody
	@PostMapping("replyDelete")
	public void replyDelete(
			@RequestParam("communityReplyId") int communityReplyId,
			@RequestParam("communityId") int communityId,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		communityService2.replyDelete(communityReplyId, communityId, user);
	}
}
