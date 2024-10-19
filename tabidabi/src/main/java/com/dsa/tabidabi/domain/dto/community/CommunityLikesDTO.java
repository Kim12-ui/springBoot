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
public class CommunityLikesDTO {
	private Integer likeId;				// 고유번호
	private Integer communityId;		// 커뮤니티 방번호
	private String memberId;			// 좋아요 누른 멤버
	private LocalDateTime createdAt;	// 좋아요 누른 시간
}
