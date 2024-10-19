package com.dsa.tabidabi.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원정보 Entity
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "members")
public class MemberEntity {

	// 사용자 아이디
	@Id
	@Column(name="member_id", nullable = false, length = 20)
	String memberId;
	
	// 비밀번호
	@Column(name="password", nullable = false, length = 255)
	String password;
	
	// 닉네임
	@Column(name="nickname", nullable = false, length = 20)
	String nickname;
	
	// 이메일
	@Column(name="email", nullable = false, length = 50)
	String email;
	
	// 프로필 이미지
	@Column(name="profile_image", nullable = true, length = 255)
	String profileImage;	
	
	// 생성시간
	@CreatedDate
	@Column(name="created_at", nullable = false, columnDefinition = "timestamp default current_timestamp")
	LocalDateTime createdAt;
}
