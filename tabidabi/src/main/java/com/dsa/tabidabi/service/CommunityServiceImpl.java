package com.dsa.tabidabi.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.tabidabi.domain.dto.Storage.StorageDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDetailsDTO;
import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageInformationDetailsEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageInformationEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityCommentsEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoDetailsEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoEntity;
import com.dsa.tabidabi.repository.MemberRepository;
import com.dsa.tabidabi.repository.Storage.StorageInformationDetailsRepository;
import com.dsa.tabidabi.repository.Storage.StorageInformationRepository;
import com.dsa.tabidabi.repository.Storage.StorageRepository;
import com.dsa.tabidabi.repository.community.CommunityCommentsRepository;
import com.dsa.tabidabi.repository.community.CommunityInfoDetailsRepository;
import com.dsa.tabidabi.repository.community.CommunityInfoRepository;
import com.dsa.tabidabi.repository.community.CommunityRepository;
import com.dsa.tabidabi.util.FileManager;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CommunityServiceImpl implements CommunityService {
	
	// 파일메니저
	private final FileManager fileManager;
	
	// 멤버 리포지토리
	private final MemberRepository mr;
	
	// 저장소 리포지토리
	private final StorageRepository sr;
	private final StorageInformationDetailsRepository sidr;
	private final StorageInformationRepository sir;
	
	// 커뮤니티 리포지토리
	private final CommunityRepository cr;
	private final CommunityInfoRepository cir;
	private final CommunityCommentsRepository ccr;
	private final CommunityInfoDetailsRepository cidr;
	
	/**
	 * From StorageEntity to StorageDTO
	 * @param StorageEntity
	 * @return StorageDTO
	 */
	private StorageDTO convertStorageDTO(StorageEntity entity) {
		return StorageDTO.builder()
				.storageId(entity.getStorageId())
				.memberId(entity.getMember().getMemberId())
				.roomTitle(entity.getRoomId().getRoomTitle())
				.createdAt(entity.getCreatedAt())
				.build();
	}
	
	/**
	 * From StorageInformationDetailsEntity to StorageInformationDetailsDTO
	 * @param StorageInformationDetailsEntity
	 * @return StorageInformationDetailsDTO
	 */
	private StorageInformationDetailsDTO convertStorageInformationDetailsDTO(StorageInformationDetailsEntity entity) {
		return StorageInformationDetailsDTO.builder()
				.informationDetailsId(entity.getInformationDetailsId())
				.storageId(entity.getStorage().getStorageId())
				.planType(entity.getPlanType())
				.title(entity.getTitle())
				.content(entity.getContent())
				.createdAt(entity.getCreatedAt())
				.build();
	}
	
	/**
	 * From StorageInformationEntity to StorageInformationDTO
	 * @param StorageInformationEntity
	 * @return StorageInformationDTO
	 */
	private StorageInformationDTO convertStorageInformation(StorageInformationEntity entity) {
		return StorageInformationDTO.builder()
				.storageInformationId(entity.getStorageInformationId())
				.storageId(entity.getStorage().getStorageId())
				.planType(entity.getPlanType())
				.storageDeparture(entity.getStorageDeparture())
				.storageReturn(entity.getStorageReturn())
				.storageContinent(entity.getStorageContinent())
				.storageCountry(entity.getStorageCountry())
				.createdAt(entity.getCreatedAt())
				.build();
	}

	/**
	 * 우리의 여행일지 -> 저장된 저장소 정보 불러오기
	 * @return storagesList
	 */
	@Override
	public List<StorageDTO> selectMyStorages(String memberId) {
		MemberEntity member = mr.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("해당 회원은 없습니다."));
		List<StorageEntity> entityList = sr.findByMember(member);
		
		List<StorageDTO> storagesList = new ArrayList<>();
		for (StorageEntity entity : entityList) {
			StorageDTO dto = convertStorageDTO(entity);
			storagesList.add(dto);
		}
		return storagesList;
	}

	/**
	 * 우리의 여행일지 -> 저장소_정보_디테일 정보 불러오기
	 * @return storageInformationDetailsList
	 */
	@Override
	public List<StorageInformationDetailsDTO> selectMyStorageInformationDetails(String memberId) {
	    // 로그인한 유저의 Storage 정보를 불러옴
	    MemberEntity member = mr.findById(memberId)
	            .orElseThrow(() -> new EntityNotFoundException("해당 회원은 없습니다."));
	    
	    // 유저가 소유한 모든 StorageEntity를 가져옴
	    List<StorageEntity> storageEntityList = sr.findByMember(member);
	    
	    // 유저가 소유한 storageId 리스트를 추출
	    List<Integer> storageIdList = new ArrayList<>();
	    for (StorageEntity storage : storageEntityList) {
	        storageIdList.add(storage.getStorageId());
	    }
	    
	    // storageId 리스트에 해당하는 StorageInformationDetailsEntity 리스트를 조회
	    List<StorageInformationDetailsEntity> entityList = sidr.findByStorageStorageIdIn(storageIdList);
	    
	    // DTO로 변환하여 반환
	    List<StorageInformationDetailsDTO> storageInformationDetailsList = new ArrayList<>();
	    for (StorageInformationDetailsEntity entity : entityList) {
	        StorageInformationDetailsDTO dto = convertStorageInformationDetailsDTO(entity);
	        storageInformationDetailsList.add(dto);
	    }
	    
	    return storageInformationDetailsList;
	}

	/**
	 * 우리의 여행일지 -> 저장소_정보 정보 불러오기
	 */
	@Override
	public List<StorageInformationDTO> selectMyStorageInformation(String memberId) {
	    // 로그인한 유저의 Storage 정보를 불러옴
	    MemberEntity member = mr.findById(memberId)
	            .orElseThrow(() -> new EntityNotFoundException("해당 회원은 없습니다."));
	    
	    // 해당 유저가 가진 Storage를 가져옴
	    List<StorageEntity> storageEntityList = sr.findByMember(member);
	    
		// 유저가 소유한 storageId 리스트를 추출
	    List<Integer> storageIdList = new ArrayList<>();
	    for (StorageEntity storage : storageEntityList) {
	        storageIdList.add(storage.getStorageId());
	    }
	    
	    // StorageInformationEntity를 조회
	    List<StorageInformationEntity> entityList = sir.findByStorageStorageIdIn(storageIdList);
	    
	    // DTO 리스트로 변환
	    List<StorageInformationDTO> storageInformationList = new ArrayList<>();
	    for (StorageInformationEntity entity : entityList) {
	        StorageInformationDTO dto = convertStorageInformation(entity);
	        storageInformationList.add(dto);
	    }
	    
	    return storageInformationList;
	}

	/**
	 * storageId & planType에 맞는 StorageInformationDetails 정보 출력
	 * @param storageId
	 * @param planType
	 * @return dtoList
	 */
	@Override
	public List<StorageInformationDetailsDTO> getList(Integer storageId, String planType) {
		List<StorageInformationDetailsEntity> entityList = 
				sidr.findByStorage_StorageIdAndPlanType(storageId, planType);
		
		List<StorageInformationDetailsDTO> dtoList = new ArrayList<>();
		for (StorageInformationDetailsEntity entity : entityList) {
			StorageInformationDetailsDTO dto = convertStorageInformationDetailsDTO(entity);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	@Override
	public StorageInformationDTO getDTO(Integer storageId, String planType) {
		StorageInformationEntity entity
		= sir.findByStorage_StorageIdAndPlanType(storageId, planType);
		
		StorageInformationDTO dto = StorageInformationDTO.builder()
									.storageId(entity.getStorage().getStorageId())
									.planType(entity.getPlanType())
									.storageDeparture(entity.getStorageDeparture())
									.storageReturn(entity.getStorageReturn())
									.storageContinent(entity.getStorageContinent())
									.storageCountry(entity.getStorageCountry())
									.build();
		return dto;
	}

	/**
	 * communities 관련 정보 테이블에 저장 (제출 버튼 클릭시)
	 * @return 커뮤니티 글쓰기 완료후 저장
	 */
	@Override
	public void saveCommunities(
			// Community 관련정보
			String memberId, String title,
			
			// CommunityInfo 관련정보
			String communityDeparture ,String communityReturn, 
			String communityContinent, String communityCountry,
			
			// communityComments 관련정보
			String communityComments, MultipartFile upload, String uploadPath,
			
			// CommunityInfoDetails 관련정보
			List<String> storageTitleList, List<String> storageContentList, 
			List<String> plusTitleList, List<String> plusContentList
			) {
		 MemberEntity member = mr.findById(memberId)
		            .orElseThrow(() -> new EntityNotFoundException("해당 회원은 없습니다."));
		 
		 // communities 테이블에 데이터 저장
		 CommunityEntity communityEntity = new CommunityEntity();
		 communityEntity.setMember(member);
		 communityEntity.setTitle(title);
		 
		 CommunityEntity savedCommunity = cr.save(communityEntity);
		 
		 // community_informations 테이블에 데이터 저장
		 CommunityInfoEntity communityInfoEntity = new CommunityInfoEntity();
		 communityInfoEntity.setCommunity(savedCommunity);
		 communityInfoEntity.setCommunityDeparture(communityDeparture);
		 communityInfoEntity.setCommunityReturn(communityReturn);
		 communityInfoEntity.setCommunityContinent(communityContinent);
		 communityInfoEntity.setCommunityCountry(communityCountry);
		 cir.save(communityInfoEntity);
		
		 // community_comments에 데이터 저장
		 CommunityCommentsEntity communityCommentsEntity = new CommunityCommentsEntity();
		 communityCommentsEntity.setCommunity(savedCommunity);
		 communityCommentsEntity.setContent(communityComments);
		 
		 if (upload != null && !upload.isEmpty()) {
			    try {
			        String fileName = fileManager.saveFile(uploadPath, upload);
			        // 실제로 저장된 파일 이름을 설정
			        communityCommentsEntity.setRepresentativeImage(fileName);
			    } catch (IOException e) {
			        // 파일 저장 중 오류 처리
			        e.printStackTrace();
			    }
			}
		 
		 ccr.save(communityCommentsEntity);
		 
		// community_information_details 테이블에 데이터 저장 
		if (storageTitleList == null && storageContentList == null &&
				 plusTitleList == null && plusContentList == null) {
		     throw new IllegalArgumentException("At least one of the title/content lists must be provided.");
		 }	 
		 
		if (storageTitleList != null && storageContentList != null) {
			// 각 항목을 community_information_details 테이블에 저장 (storage)
		    for (int i = 0; i < storageTitleList.size(); i++) {
		        String storageTitle = storageTitleList.get(i);
		        String storageContent = storageContentList.get(i);

		        // CommunityInformationDetailsEntity를 생성하여 데이터 저장
		        CommunityInfoDetailsEntity communityInfoDetailsEntity = new CommunityInfoDetailsEntity();
		        communityInfoDetailsEntity.setCommunity(savedCommunity);
		        communityInfoDetailsEntity.setTitle(storageTitle);
		        communityInfoDetailsEntity.setContent(storageContent);
		        // 저장 처리
		        cidr.save(communityInfoDetailsEntity);
		    }
		}
	    
	    if (plusTitleList != null && plusContentList != null) {
	    	// 각 항목을 community_information_details 테이블에 저장 (plus)
		    for (int i = 0; i < plusTitleList.size(); i++) {
		        String plusTitle = plusTitleList.get(i);
		        String plusContent = plusContentList.get(i);

		        // CommunityInformationDetailsEntity를 생성하여 데이터 저장
		        CommunityInfoDetailsEntity communityInfoDetailsEntity = new CommunityInfoDetailsEntity();
		        communityInfoDetailsEntity.setCommunity(savedCommunity);;
		        communityInfoDetailsEntity.setTitle(plusTitle);
		        communityInfoDetailsEntity.setContent(plusContent);
		        // 저장 처리
		        cidr.save(communityInfoDetailsEntity);
		    }
	    }
	    
	}

}
