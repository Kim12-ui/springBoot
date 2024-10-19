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
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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
@Table(name="community_comments")
public class CommunityCommentsEntity {
	// 커뮤니티_코멘트 고유번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_comments_id")
	private Integer communityCommentsId;
	
	// 커뮤니티 아이디
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
	private CommunityEntity community;
	
	// 코멘트 작성 내용
	@Lob
    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String content;
	
	// 대표 이미지
	@Column(name="representative_image", length = 255)
	private String representativeImage;
	
	// 생성 날짜
	@CreatedDate
	@Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	
	// 수정 날짜
	@LastModifiedDate
	@Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime updatedAt;
}
