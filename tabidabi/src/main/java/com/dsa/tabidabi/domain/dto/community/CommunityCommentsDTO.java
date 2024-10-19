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
public class CommunityCommentsDTO {
	private Integer communityCommentsId; // 커뮤니티_코멘트 고유번호
    private Integer communityId;         // 커뮤니티 아이디
    private String content;              // 코멘트 작성 내용
    private String representativeImage;  // 대표 이미지
    private LocalDateTime createdAt;     // 생성시간
    private LocalDateTime updatedAt;     // 수정시간
}
