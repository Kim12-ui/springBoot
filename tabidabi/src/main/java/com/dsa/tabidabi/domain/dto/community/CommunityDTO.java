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
public class CommunityDTO {
	private Integer communityId;  		// 커뮤니티방 고유 번호
    private String memberId;       		// 작성자 멤버 아이디
    private String nickname;			// 작성자 닉네임
    private String title;          		// 커뮤니티 제목
    private Integer likeCount;     		// 좋아요 수
    private Integer viewCount;     		// 조회 수
    private LocalDateTime createdAt; 	// 생성 시간
    private LocalDateTime updatedAt; 	// 업데이트 시간
    private List<ReplyDTO> replyList;	// 리플목록
    private List<CommunityInfoDetailsDTO> communityInfoDetailsList;	// 정보_상세목록
    private List<CommunityLikesDTO> communityLikesList;				// 좋아요
}
