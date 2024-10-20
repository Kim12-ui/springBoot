package com.dsa.tabidabi.repository.community;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.community.ReplyEntity;

@Repository
public interface ReplyRepository  extends JpaRepository<ReplyEntity, Integer>{

	List<ReplyEntity> findByCommunity_CommunityId(int communityId, Sort sort);

}
