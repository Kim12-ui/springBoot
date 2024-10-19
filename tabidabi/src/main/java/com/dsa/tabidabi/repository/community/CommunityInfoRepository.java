package com.dsa.tabidabi.repository.community;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.community.CommunityInfoEntity;

@Repository
public interface CommunityInfoRepository extends JpaRepository<CommunityInfoEntity, Integer> {

}
