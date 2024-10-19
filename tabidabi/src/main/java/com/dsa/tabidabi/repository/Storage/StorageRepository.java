package com.dsa.tabidabi.repository.Storage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.Storage.StorageEntity;

@Repository
public interface StorageRepository extends JpaRepository<StorageEntity, Integer> {
	// 특정 회원에 일치하는 StorageEntity 리스트 조회
    List<StorageEntity> findByMember(MemberEntity member);

}
