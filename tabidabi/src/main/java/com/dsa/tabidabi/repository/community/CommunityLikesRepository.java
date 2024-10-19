package com.dsa.tabidabi.repository.community;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityLikesEntity;

@Repository
public interface CommunityLikesRepository extends JpaRepository<CommunityLikesEntity, Integer> {

	CommunityLikesEntity findByCommunityAndMember(CommunityEntity communityEntity, MemberEntity memberEntity);

	@Query("SELECT cl.community FROM CommunityLikesEntity cl WHERE cl.member = :member")
    List<CommunityEntity> findByMember(@Param("member") MemberEntity member);
		
	
	

}
