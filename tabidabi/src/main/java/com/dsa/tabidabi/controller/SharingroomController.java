package com.dsa.tabidabi.controller;

import java.util.HashMap; // HashMap import
import java.util.List;
import java.util.Map; // Map import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsa.tabidabi.domain.dto.Storage.StorageDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDetailsDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.ScheduleRequest;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomApiDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDetailsDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomParticipantsDTO;
import com.dsa.tabidabi.security.AuthenticatedUser;
import com.dsa.tabidabi.service.SharingroomService;
import com.dsa.tabidabi.service.sharingroom.SharingRoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/sharingroom")
@RequiredArgsConstructor
public class SharingroomController {
	
	private final SharingRoomService sharingRoomService;
	
	
	/**
	 *  요청시 일정 공유방 페이지로 이동
	 *  @return sharingroom.html
	 */
	@GetMapping("/sharingroom")
		public String sharingroom(@AuthenticationPrincipal AuthenticatedUser user
									,Model model) {
		
		// 요청 들어올때 sharinroom테이블에 데이터가 들어가야함.
		//roomChief : 방장 멤버ID = 이 버튼을 클릭한 유저ID 
		//roomTitle : null
		
		//현재 로그인한 사용자 정보 가져오기
		String roomChief = user.getId();
		
		
		//리턴된 response 받기
		Map<String, Integer> response = sharingRoomService.createSharingRoom(roomChief);
		
	
		
		//생성된 roomId를 모델에 추가하여 클라이언트에 전달
		model.addAttribute("roomId", response.get("roomId : " ));
		model.addAttribute("storageId",response.get("storageId :")); 
		log.info("모델 :{}  ", model);
		log.info("룸 아이디2: {}, 스토리지 아이디: {}", response.get("roomId : "), response.get("storageId :"));
		return "sharingroom";
		//return "redirect:/sharingroom/sharingroom/" + response.get("roomId : ");
		}
	
	/**
	 * 특정 roomId를 가진 일정 공유방 페이지로 이동
	 * @param roomId 공유방 ID
	 * @return sharingroom.html
	 */
	@GetMapping("/sharingroom/{roomId}")
	public String sharingRoomWithId(@PathVariable("roomId") Integer roomId, Model model) {
	    log.info("룸 아이디:{} " , roomId);
	    // roomId를 모델에 추가하여 클라이언트에 전달
	    model.addAttribute("roomId", roomId);
	    log.info("id: {}" ,roomId);
	    
	    return "sharingroom";
	}

	
	
	
	// http://localhost:4444/sharingroom/saveSchedule 
	
	/**
	 * 일정공유방에서 온 데이터를 받아서 처리
	 * 
	 */
	// requestData가 @RequestBody 어노테이션으로 ScheduleRequest DTO에 자동으로 매핑
	@PostMapping("/saveSchedule")
	public ResponseEntity<Map<String, String>> saveSchedule(@RequestBody ScheduleRequest request){
		
		
		log.info("saveSchedule컨트롤러 실행");
		
		System.out.println("받은 데이터 -  request: " + request);
		
		try {
		//최상위 DTO에서 하위DTO들을 가져와 사용
		 SharingroomApiDTO sharingroomApi = request.getSharingroomApi();
		 SharingroomDTO sharingroom = request.getSharingroom();
		 SharingroomInformationDTO sharingroomInformation = request.getSharingroomInformation();
		 SharingroomInformationDetailsDTO sharingroomInformationDetails = request.getSharingroomInformationDetails();
		 SharingroomParticipantsDTO sharingroomParticipants = request.getSharingroomParticipants();
		 
		 StorageDTO  storage = request.getStorage();
		 StorageInformationDTO storageInformation  = request.getStorageInformation();
		 StorageInformationDetailsDTO storageInformationDetails = request.getStorageInformationDetails();
		
		 log.info("sharingroomApiDTO 정보 : {}", sharingroomApi);
		 log.info("haringroomInformationDTO 정보 : {} ", sharingroomInformation);
		 log.info("sharingroomInformationDetailsDTO 정보 : {}" , sharingroomInformationDetails);
		 log.info("일정제목 : {}", sharingroomInformationDetails.getTitles() );


		 
		 //서비스 호출
		 sharingRoomService.saveSharingRoom(sharingroomApi, sharingroom, sharingroomInformation, sharingroomInformationDetails, sharingroomParticipants);
	        
		 // 로직 수행 후 응답
		 Map<String, String> response = new HashMap<>(); // HashMap 생성
	        response.put("message", "일정이 성공적으로 저장되었습니다!");

	        return ResponseEntity.ok(response);
	}catch (Exception e) {
		log.error("일정 저장 중 오류 발생: {}", e.getMessage());
		// 에러 응답
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "일정 저장 중 오류가 발생했습니다.");
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
	}
}
