package com.dsa.tabidabi.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

	// 사용자 아이디
	private String memberId;
	
	// 비밀번호
	private String password;
	
	// 닉네임
	private String nickname;
	
	// 이메일
	private String email;
	
	// 프로필 이미지
	private String profileImage;
	
	// 생성시간
	private LocalDateTime createdAt;
}
