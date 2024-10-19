package com.dsa.tabidabi.service.sharingroom;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomApiDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDetailsDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomParticipantsDTO;
import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageInformationDetailsEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageInformationEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomApiEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomInformationDetailsEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomInformationEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomParticipantsEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomsEntity;
import com.dsa.tabidabi.repository.MemberRepository;
import com.dsa.tabidabi.repository.Storage.StorageInformationDetailsRepository;
import com.dsa.tabidabi.repository.Storage.StorageInformationRepository;
import com.dsa.tabidabi.repository.Storage.StorageRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomApiRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomInformationDetailsRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomInformationRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomParticipantsRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class SharingRoomServiceImpl implements SharingRoomService {
	
	private final SharingroomApiRepository sharingroomApiRep;
	private final SharingroomInformationRepository sharingroomInformationRep;
	private final SharingroomInformationDetailsRepository sharingroomInformationDetailsRep;
	private final SharingroomParticipantsRepository sharingroomParticipantsRep;
	private final SharingroomRepository sharingroomRep;
	private final MemberRepository memberRep;
	private final StorageRepository storageRep;
	private final StorageInformationRepository storageInformationRep;
	private final StorageInformationDetailsRepository storageInformationDetailsRep;
//	private fianl Storage api repository
	
	/**
	 * 방 생성기능 
	 */
	@PersistenceContext
    private EntityManager entityManager;
	@Override
	public  Map<String, Integer> createSharingRoom(String memberId) {
		log.info("방 생성 서비스 호출됨: memberId={}", memberId);
		

		//MemberEntity를 repository를 통해 찾아오기
		MemberEntity entity = memberRep.findById(memberId)
								 .orElseThrow(() -> new RuntimeException("방장 ID를 찾을 수 없습니다"));
								
		log.info("방장 조회 성공: {}", entity);
		// 방 객체 만들기
		SharingroomsEntity sharingRoom = new SharingroomsEntity();
		log.info("저장된 방 정보: {}", sharingRoom);
		
		//방장 설정
		sharingRoom.setRoomChief(entity);
		sharingRoom.setRoomTitle("방 제목"); // roomTitle은 null로 설정
		
		//방 저장(savedRoom에 자동 생성된 roomId가 함께 저장)
		SharingroomsEntity savedRoom = sharingroomRep.save(sharingRoom);
		System.out.println("Saved room: " + savedRoom);
		log.info("방 저장 완료: {}", sharingRoom);
	
		
		//sharingroom_participants 테이블 데이터 넣기
		SharingroomParticipantsEntity Participants = new SharingroomParticipantsEntity(); //객체 만들기
		Participants.setParticipantMember(entity); // 방장 정보를 참여자에 설정
	    Participants.setParticipantRoom(savedRoom); // participantRoom에 SharingroomsEntity 객체 설정
		
	    // participants 엔티티 저장
	    sharingroomParticipantsRep.save(Participants);
	    
	    //sharingroom_information : 공유방 정보 테이블 데이터 넣기
	    SharingroomInformationEntity Information = new SharingroomInformationEntity(); // 객체 만들기
	    Information.setInformationRoom(savedRoom);
	    
	    //엔티티 저장
	    sharingroomInformationRep.save(Information);
	    
	    //sharingroom_information_details : 공유방 상세정보 데이터 넣기
	    SharingroomInformationDetailsEntity details = new SharingroomInformationDetailsEntity();
	    details.setInformationRoom(savedRoom);
	    
	    //엔티티 저장
	    sharingroomInformationDetailsRep.save(details);
	    
	    // sharingroom_api 테이블 데이터 넣기
	    SharingroomApiEntity sharingApi = new SharingroomApiEntity();
	    sharingApi.setRoomApiId(savedRoom);
	    sharingApi.setLat(0);
	    sharingApi.setLng(0);
	    sharingApi.setPlanType("A");
	    sharingApi.setPlace("장소A");
	    
	    //엔티티 저장 
	    sharingroomApiRep.save(sharingApi);
	    
	    // storages : 저장소(우리가만든동선) 테이블 데이터 넣기
	    StorageEntity storage = new StorageEntity();
	    storage.setMember(entity);   //현재 방장에 대해서만 들어감, 방에 참여해서 들어갈때 똑같이 데이터 넣어줘야한다.
	    
	    //엔티티 저장하고 storageId가 있는 entity객체 가져오기 
	    StorageEntity storageId= storageRep.save(storage);
	    log.info("storageId가 있는 entity객체 {} : ",storageId );
	    
	    // storage_information 테이블 데이터 넣기
	    StorageInformationEntity stoInfo = new StorageInformationEntity();
	    stoInfo.setStorage(storageId);
	    
	    // 엔티티 저장
	    storageInformationRep.save(stoInfo);
	    
	    //storage_information_details 테이블 데이터 넣기
	    StorageInformationDetailsEntity stoInfoDetail = new StorageInformationDetailsEntity();
	    stoInfoDetail.setStorage(storageId);
	    
	    //엔티티 저장
	    storageInformationDetailsRep.save(stoInfoDetail);
	    
	    //storage_api : storage_id 저장 
	    

		Map<String, Integer> response = new HashMap<>();
		response.put("roomId : ", savedRoom.getRoomId() );
		response.put("storageId :", storageId.getStorageId());
		
	
		
		entityManager.flush();
		
		log.info("id: ",savedRoom.getRoomId());
		
		//생성된 roomId반환
		return response;
		 
		
		

	}
	 // 1차 캐시 비우기
    public void clearCache() {
        entityManager.clear();
    }
    
    
    /**
     * 저장하기 버튼 : 데이터 저장하기 
     */
	@Override
	public void saveSharingRoom(SharingroomApiDTO sharingroomApi, 
			SharingroomDTO sharingroom,
			SharingroomInformationDTO sharingroomInformation,
			SharingroomInformationDetailsDTO sharingroomInformationDetails,
			SharingroomParticipantsDTO sharingroomParticipants) {
		
		log.info("공유방정보저장 서비스 실행");
		//데이터 가져오기
		//roomId 가져오기 
		Integer roomId = sharingroom.getRoomId();
		//여행 출발일 가져오기
		String infoDeparture = sharingroomInformation.getInformationDeparture();
		//여행 도착일 가져오기
		String infoReturn = sharingroomInformation.getInformationReturn();
		//여행 대륙 가져오기
		String continent = sharingroomInformation.getInformationContinent();
		//여행 나라 가져오기
		String country = sharingroomInformation.getInformationCountry();
		//여행일정이름 가져오기
		String title = sharingroomInformationDetails.getTitle();
		
		//방 정보 엔티티를 가져오기 위해 roomId를 사용하여 SharingroomsEntity조회
		SharingroomsEntity sharingRoomEntity = sharingroomRep.findById(roomId)
											   .orElseThrow(() -> new RuntimeException("방 ID를 찾을 수 없습니다."));
		
		log.info("방정보 entity",sharingRoomEntity );
		
		//여행 정보를 sharingroom information 테이블에 저장
		//roomId를 sharingroom_information 테이블에 저장
		//sharingroomInformation.setInformationRoom(sharingRoomEntity);
		//여행 출발일 저장
		sharingroomInformation.setInformationDeparture(infoDeparture);
		//여행 도착일 저장
		sharingroomInformation.setInformationReturn(infoReturn);
		//여행 대륙 저장
		sharingroomInformation.setInformationContinent(continent);
		//여행 나라 저장
		sharingroomInformation.setInformationCountry(country);
		
		//테이블에 저장(sharingroom_information : 공유방 정보테이블)
		
		//DTO를 Entity로 변환
		SharingroomInformationEntity sharingroomInformationEntity = convertToEntity(sharingroomInformation);
		log.info("공유방 정보 entity확인 {} : ", sharingroomInformationEntity);
		
		
		//저장
		sharingroomInformationRep.save(sharingroomInformationEntity);
		
		//여행 세부정보를 sharingroom_information_details 테이블에 저장
		//해당 roomId저장
//		sharingroomInformationDetails.setInformationRoom(roomId);
//		//일정이름 저장
//		sharingroomInformationDetails.setTitle(title);
		
	}
	private SharingroomInformationEntity convertToEntity(SharingroomInformationDTO dto) {
		SharingroomInformationEntity entity = new SharingroomInformationEntity();
		//entity.setInformationRoom(dto.getInformationRoom());
		entity.setInformationDeparture(dto.getInformationDeparture());
		entity.setInformationReturn(dto.getInformationReturn());
		entity.setInformationCountry(dto.getInformationContinent());
		entity.setInformationCountry(dto.getInformationCountry());
		
		
		
		return entity;
	}
	
}
