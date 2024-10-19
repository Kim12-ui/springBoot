package com.dsa.tabidabi.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.dto.community.CommunityInfoDetailsDTO;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoDetailsEntity;

@Repository
public interface CommunityInfoDetailsRepository extends JpaRepository<CommunityInfoDetailsEntity, Integer> {

	/**
	 * 
	 * @param communityId
	 * @return
	 */
	//List<CommunityInfoDetailsDTO> getPostsBycommunityId(Integer communityId);
	
	 // JPQL 쿼리 작성
    @Query("SELECT d " +
           "FROM CommunityInfoDetailsEntity d " +
           "WHERE d.community.communityId = :communityId")
    List<CommunityInfoDetailsDTO> getPostsByCommunityId(@Param("communityId") Integer communityId);

}
