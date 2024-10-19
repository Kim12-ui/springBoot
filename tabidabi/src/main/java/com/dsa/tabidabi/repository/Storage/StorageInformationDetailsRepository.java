package com.dsa.tabidabi.repository.Storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.Storage.StorageEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageInformationDetailsEntity;

@Repository
public interface StorageInformationDetailsRepository extends JpaRepository<StorageInformationDetailsEntity, Integer> {
	// StorageEntity에 해당하는 StorageInformationEntity 리스트 조회
    List<StorageInformationDetailsEntity> findByStorage(StorageEntity storage);
	
    // 특정 StorageId들에 해당하는 StorageInformationDetailsEntity 리스트 조회
    List<StorageInformationDetailsEntity> findByStorageStorageIdIn(List<Integer> storageIds);
    
    List<StorageInformationDetailsEntity> findByStorage_StorageIdAndPlanType(Integer storageId, String planType);
}