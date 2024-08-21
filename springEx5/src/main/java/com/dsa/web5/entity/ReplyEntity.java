package com.dsa.web5.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 리플 정보 엔티티
 */
@Builder
@Getter
@Setter
@ToString(exclude = "board")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "web5_reply")
public class ReplyEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="reply_num")
	private Integer replyNum;
	
	// 다대일 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id",referencedColumnName="member_id")
	private MemberEntity member;
	
	@Column(name = "contents", nullable = false, length = 2000)
	private String contents;
	
	@CreatedDate
	@Column(name = "create_date", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createDate;
	
	// 다대일 관계, 여러 댓글이 하나의 게시글에 속함
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_num")
	private BoardEntity board;
}
