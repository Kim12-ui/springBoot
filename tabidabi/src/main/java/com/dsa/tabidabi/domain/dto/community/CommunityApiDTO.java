package com.dsa.tabidabi.domain.dto.community;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityApiDTO {
	private Integer communityApiId;	// 고유번호
	private Integer CommunityId;	// 커뮤니티 방 번호
	private String place;			// 장소 이름
	private Double lat;				// 좌표1
	private Double lng;				// 좌표2
}
