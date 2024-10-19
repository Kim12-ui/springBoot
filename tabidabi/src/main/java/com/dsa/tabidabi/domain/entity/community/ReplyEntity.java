package com.dsa.tabidabi.domain.entity.community;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dsa.tabidabi.domain.entity.MemberEntity;

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
@Table(name = "community_reply")
public class ReplyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_reply_id")
	private Integer communityReplyId;
	
	//다대일 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="community_id", referencedColumnName = "community_id")
	private CommunityEntity community;
	
	@Column(name = "reply_content", columnDefinition = "TEXT")
	private String replyContent;
	
	@CreatedDate
	@Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	

	
	
	
	
	
	
	
	
	
	
	
	
		
}
