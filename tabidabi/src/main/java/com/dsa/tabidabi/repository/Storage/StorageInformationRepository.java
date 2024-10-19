package com.dsa.tabidabi.repository.Storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.Storage.StorageEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageInformationEntity;

@Repository
public interface StorageInformationRepository extends JpaRepository<StorageInformationEntity, Integer> {
	// StorageEntity에 해당하는 StorageInformationEntity 리스트 조회
    List<StorageInformationEntity> findByStorage(StorageEntity storage);

	List<StorageInformationEntity> findByStorageStorageIdIn(List<Integer> storageIdList);

	StorageInformationEntity findByStorage_StorageIdAndPlanType(Integer storageId, String planType);
	
}
