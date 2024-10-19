package com.dsa.tabidabi.domain.dto.sharingroom;

import com.dsa.tabidabi.domain.dto.Storage.StorageDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDetailsDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleRequest {
	
	//컴포지션(Composition) : 하나의 최상위 DTO에 여러 하위 DTO 객체를 필드로 포함하는 형태
	 
	 /*
	  * roomId, planType, place, lat, lng
	  */
	 private SharingroomApiDTO sharingroomApi;
	 
	 /**
	  * roomId, roomChief, roomTitle, 
	  */
	 private SharingroomDTO sharingroom;
	 
	 /**
	  * planType, 출발일, 복귀일, 대륙, 나라, 경비, 사람수 
	  */
	 private SharingroomInformationDTO sharingroomInformation;
	 
	 /**
	  * 계획타입, 세부일정이름, 세부일정내용, 
	  */
	 private SharingroomInformationDetailsDTO sharingroomInformationDetails;
	 
	 /**
	  * 멤버 아이디, ...
	  */
	 private SharingroomParticipantsDTO sharingroomParticipants;
	 
	 /**
	  * 저장소
	  */
	 private StorageDTO storage;
	 
	 /**
	  * 저장소_정보
	  */
	 private StorageInformationDTO storageInformation;
	 
	 /**
	  * 저장소_상세정보
	  */
	 private StorageInformationDetailsDTO storageInformationDetails;
}
