package com.dsa.tabidabi.domain.entity.community;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="community_information_details")
public class CommunityInfoDetailsEntity {
	// 커뮤니티_정보_디테일 고유정보
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_details_id")
	private Integer communityDetailsId;
	
	// 커뮤니티 아이디
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
	private CommunityEntity community;
	
	// 일정 제목
	@Column(name="title", length=255)
	private String title;
	
	// 상세일정 입력란
	@Column(name = "content", columnDefinition = "TEXT")
    private String content;
	
	// 생성 날짜
	@CreatedDate
	@Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	
	// 수정 날짜
	@LastModifiedDate
	@Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime updatedAt;
}