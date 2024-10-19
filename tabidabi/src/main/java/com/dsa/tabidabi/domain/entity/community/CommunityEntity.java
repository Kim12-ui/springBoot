package com.dsa.tabidabi.domain.entity.community;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dsa.tabidabi.domain.entity.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="communities")
public class CommunityEntity {
	// 커뮤니티방 고유 번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_id")
	private Integer communityId;
	
	// 작성자 멤버 아이디
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id")
	private MemberEntity member;
	
	// 커뮤니티 제목
	@Column(name = "title", nullable = false, length = 255)
	private String title;
	
	// 좋아요 수
	@Column(name = "like_count", columnDefinition = "integer default 0")
	private Integer likeCount = 0;
	
	// 조회 수
	@Column(name = "view_count", columnDefinition = "integer default 0")
	private Integer viewCount = 0;
	
	// 생성 시간
    @CreatedDate
    @Column(name = "created_at", updatable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    // 업데이트 시간
    @LastModifiedDate
    @Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime updatedAt;
	
    // OneToMany 관계
	// 리플목록
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReplyEntity> replyList;
    
	// 정보_상세목록
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityInfoDetailsEntity> communityInfoEdtailsList;
	
	// 좋아요목록
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityLikesEntity> communityLikesList;
    
    // API목록
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommunityApiEntity> communityApiList;
    
    // OneToOne 관계
    // CommunityInfoEntity
    @OneToOne(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CommunityInfoEntity communityInfoEntity;
    
    // CommunityCommentsEntity
    @OneToOne(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CommunityCommentsEntity communityCommentsEntity;
}
