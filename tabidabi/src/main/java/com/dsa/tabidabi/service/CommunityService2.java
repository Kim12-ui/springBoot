package com.dsa.tabidabi.service;

//import com.dsa.tabidabi.entity.Community;
import java.util.List;

import org.springframework.data.domain.Page;

import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityInfoDetailsDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityListDTO;
import com.dsa.tabidabi.domain.dto.community.ReplyDTO;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;
import com.dsa.tabidabi.security.AuthenticatedUser;

public interface CommunityService2 {

	Page<CommunityListDTO> getSearchResult(String continent, String country, String title, int page, int pageSize);

	Boolean like(Integer communityId, AuthenticatedUser user);

	List<CommunityInfoDetailsDTO> getPostsBycommunityId(Integer communityId);
	/**
	 * 특정 회원이 작성한 커뮤니티 목록 가져오기
	 * @param memberId 회원 ID
	 * @return CommunityDTO 리스트
	 */
	List<CommunityDTO> getCommunitiesByMember(String memberId);

	CommunityEntity findById(Integer communityId);

	List<CommunityDTO> selectLikedList(String memberId);

	void replyWrite(int communityId, String replyContent, String memberId);

	List<ReplyDTO> getReplyList(int communityId);

	void replyDelete(int communityReplyId, int communityId, AuthenticatedUser user);

	Boolean selectlike(Integer communityId, AuthenticatedUser user);
	
	Integer findNextCommunityId(Integer communityId);
	
	Integer findPreviousCommunityId(Integer communityId);
}