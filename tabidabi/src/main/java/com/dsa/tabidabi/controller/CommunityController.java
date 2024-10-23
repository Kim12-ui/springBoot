package com.dsa.tabidabi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.tabidabi.domain.dto.Storage.StorageDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDetailsDTO;
import com.dsa.tabidabi.security.AuthenticatedUser;
import com.dsa.tabidabi.service.CommunityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("community")
@RequiredArgsConstructor
public class CommunityController {
	
	private final CommunityService cs;
	
	// application.properties 파일의 게시판 관련 설정값
	// import org.springframework.beans.factory.annotation.Value; 실행
	@Value("${community.uploadPath}")
	String uploadPath;
	
	/**
	 * 커뮤니티 글쓰기
	 * @return write.html
	 */
	@GetMapping("write")
	public String write(Model model, @AuthenticationPrincipal AuthenticatedUser user) {
		String memberId = user.getUsername();
		
		// DB에 저장된 Storages List 출력
		List<StorageDTO> storagesList = cs.selectMyStorages(memberId);

		// DB에 저장된 StorageInformation List 출력
		List<StorageInformationDTO> storageInformationList
		= cs.selectMyStorageInformation(memberId);
		
		// List의 model을 html에 전달
		model.addAttribute("storagesList",storagesList);
		model.addAttribute("storageInformationList",storageInformationList);
		return "community/write";
	}
	
	/**
	 * Ajax 이용해서 StorageInformationDetailsDTO 리스트 불러오기
	 * StorageInformationDTO 객체 불러오기
	 * planType / storageId에 해당하는 정보만 추출할 것
	 */
	@ResponseBody
	@PostMapping("selectmovement")
	public ResponseEntity<?> selectMovement(
			@RequestParam("planType") String planType,
			@RequestParam("storageId") Integer storageId
			) {
		System.out.println(planType);
		System.out.println(storageId);
		log.debug("전달된 플랜 타입 : {}, 전달된 storageId: {}", planType, storageId);
		
		// StorageInformationDetailsDTO 리스트 불러오기
		List<StorageInformationDetailsDTO> list = cs.getList(storageId,planType);
		
		// StorageInformationDTO 객체 불러오기
		StorageInformationDTO dto
		= cs.getDTO(storageId, planType);
		
		// 여러 객체를 담기 위한 Map 생성
		Map<String, Object> response = new HashMap<>();
		response.put("list", list);
		response.put("dto", dto);
		
		return ResponseEntity.ok(response);
	}
    
	/**
	 * 글쓰기 후 데이터 저장
	 * @param title
	 * @param user
	 * @param communityDeparture
	 * @param communityReturn
	 * @param communityContinent
	 * @param communityCountry
	 * @param communityComments
	 * @param upload
	 * @param storageTitleList
	 * @param storageContentList
	 * @param plusTitleList
	 * @param plusContentList
	 * @return list.html
	 */
	@PostMapping("write")
	public String write(
			// Community 관련정보
			@RequestParam("title") String title,	// 커뮤니티 제목
			@AuthenticationPrincipal AuthenticatedUser user,
			
			// CommunityInfo 관련정보
			@RequestParam("communityDeparture") String communityDeparture,	// 출발일
			@RequestParam("communityReturn") String communityReturn,		// 도착일
			@RequestParam("communityContinent") String communityContinent,	// 대륙
			@RequestParam("communityCountry") String communityCountry,		// 나라
			
			// CommunityComments 관련정보
			@RequestParam("communityComments") String communityComments,
			@RequestParam(name = "upload", required = false) MultipartFile upload,
			
			// CommunityInfoDetails 관련정보
			@RequestParam(name = "storageTitle", required = false) List<String> storageTitleList,
			@RequestParam(name = "storageContent", required = false) List<String> storageContentList,
			@RequestParam(name = "plusTitle", required = false) List<String> plusTitleList,
			@RequestParam(name = "plusContent", required = false) List<String>  plusContentList
			) {
		// 멤버아이디 변수 선언
		String memberId = user.getUsername();
		
		// log.debug
		log.debug("작성자 : {}", memberId);
		log.debug("커뮤니티 글 제목 : {}", title);
		log.debug("출발일 : {}",communityDeparture);
		log.debug("도착일 : {}",communityReturn);
		log.debug("대륙 : {}",communityContinent);
		log.debug("나라 : {}",communityCountry);
		log.debug("summernote에디터 입력 내용 : {}",communityComments);
		
		try {
			// communities 테이블에 데이터 저장
			cs.saveCommunities(
					// Community 관련정보
					memberId, title,
					
					// CommunityInfo 관련정보
					communityDeparture, communityReturn, 
					communityContinent, communityCountry,
					
					// CommunityComments 관련정보
					communityComments, upload, uploadPath,
					
					// CommunityInfoDetails 관련정보
					storageTitleList, storageContentList,
					plusTitleList, plusContentList
					);
			
			return "redirect:/community/list";
		} catch (Exception e) {
			e.printStackTrace();
			return "community/write";
		}
	}
	
	/**
	 * 커뮤니티 데이터 삭제
	 * @param communityId 해당 커뮤니티 게시글
	 * @return list.html
	 */
	@GetMapping("delete")
	public String delete(
			@RequestParam("communityId") int communityId,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		cs.delete(communityId, user.getId());
		return "community/list";
	}
	
}
