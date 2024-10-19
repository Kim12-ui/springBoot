package com.dsa.tabidabi.service.home;

import java.util.List;

import org.springframework.data.domain.Page;

import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityListDTO;
import com.dsa.tabidabi.security.AuthenticatedUser;

public interface HomeService {

	Page<CommunityListDTO> getPopularSearchResult(String continent, String country, String title, int page, int pageSize);

	Boolean like(Integer communityId, AuthenticatedUser user);

	List<CommunityDTO> selectLikedList(String memberId);

}
