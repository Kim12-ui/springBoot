package com.dsa.tabidabi.domain.dto.community;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityInfoDetailsDTO {
	private Integer communityDetailsId;         // 커뮤니티_정보_디테일 고유정보
    private Integer communityId;                // 커뮤니티 아이디
    private String title;                   	// 일정 제목
    private String content;                 	// 상세일정 입력란
    private LocalDateTime createdAt;        	// 생성시간
    private LocalDateTime updatedAt;        	// 수정시간
}
