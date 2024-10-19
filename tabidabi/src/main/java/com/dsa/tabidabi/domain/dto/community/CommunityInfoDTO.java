package com.dsa.tabidabi.domain.dto.community;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (2) 커뮤니티_동선정보
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityInfoDTO {
	private Integer communityInformationsId;    // 커뮤니티_정보 고유정보
    private Integer communityId;                // 커뮤니티 아이디
    private String communityDeparture;   		// 여행 출발일
    private String communityReturn;      		// 여행 복귀일
    private String communityContinent;      	// 여행 대륙
    private String communityCountry;        	// 여행 국가
    private LocalDateTime createdAt;        	// 생성 날짜
    private LocalDateTime updatedAt;        	// 수정 날짜
}
