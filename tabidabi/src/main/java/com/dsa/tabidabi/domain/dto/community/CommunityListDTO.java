package com.dsa.tabidabi.domain.dto.community;

import com.dsa.tabidabi.domain.dto.MemberDTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CommunityListDTO {
	private CommunityDTO communityDTO;
	private CommunityInfoDTO communityInfoDTO;
	private CommunityCommentsDTO communityCommentsDTO;
	private MemberDTO memberDTO;
}
