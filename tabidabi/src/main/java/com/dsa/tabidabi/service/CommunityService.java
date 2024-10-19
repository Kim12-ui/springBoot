package com.dsa.tabidabi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dsa.tabidabi.domain.dto.Storage.StorageDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDTO;
import com.dsa.tabidabi.domain.dto.Storage.StorageInformationDetailsDTO;

public interface CommunityService {

	// DB에 저장된 Storages List 출력
	List<StorageDTO> selectMyStorages(String memberId);

	// DB에 저장된 StorageInformationDetails List 출력
	List<StorageInformationDetailsDTO> selectMyStorageInformationDetails(String memberId);

	// DB에 저장된 StorageInformation List 출력
	List<StorageInformationDTO> selectMyStorageInformation(String memberId);

	// 모달에서 StorageId, 
	List<StorageInformationDetailsDTO> getList(Integer storageId, String planType);
	
	StorageInformationDTO getDTO(Integer storageId, String planType);

	void saveCommunities(
			// Community 관련정보
			String memberId, String title, 
			
			// CommunityInfo 관련정보
			String communityDeparture ,String communityReturn, 
			String communityContinent, String communityCountry, 
			
			// CommunityComments 관련정보
			String communityComments, MultipartFile upload, String uploadPath, 
			
			// CommunityInfoDetails 관련정보
			List<String> storageTitleList, List<String> storageContentList, 
			List<String> plusTitleList, List<String> plusContentList
			);

}
