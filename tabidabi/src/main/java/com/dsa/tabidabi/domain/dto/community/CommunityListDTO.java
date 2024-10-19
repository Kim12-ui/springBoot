package com.dsa.tabidabi.domain.dto.community;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommunityListDTO {
	private CommunityDTO communityDTO;
	private CommunityInfoDTO communityInfoDTO;
	private CommunityCommentsDTO communityCommentsDTO;
}
