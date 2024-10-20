package com.dsa.tabidabi.domain.dto.community;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	private Integer communityReplyId; 
	private Integer communityId;
	private String communityMemberId; //커뮤니티 작성자 닉네임
	private String replyMemberNickname;	// 댓글 작성자 아이디
	private String replyMemberId; // 댓글 작성자
	private String replyContent; // 댓글 내용
	private LocalDateTime createdAt; // 생성시간
}
