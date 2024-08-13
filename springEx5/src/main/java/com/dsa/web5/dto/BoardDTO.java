package com.dsa.web5.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 정보 DTO
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
	private Integer boardNum;			// 게시글 일련번호
	private String memberId;			// 작성자 ID
	private String memberName;			// 작성자 이름
	private String title;				// 글 제목
	private String contents;			// 글 내용
	private Integer viewCount;			// 조회수
	private Integer likeCount;			// 추천수
	private String originalName;		// 첨부파일 원래 이름
	private String fileName;			// 첨부파일 저장된 이름
	private LocalDateTime createDate; 	// 작성시간
	private LocalDateTime updateDate; 	// 수정시간
	private List<ReplyDTO> replyList; 	// 리플목록
}
