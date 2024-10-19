package com.dsa.tabidabi.domain.entity.sharingroom;

import java.time.LocalDateTime;

import com.dsa.tabidabi.domain.entity.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Table(name = "sharingroom_chat")
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomChatEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int chatId;

	@ManyToOne
	@JoinColumn(name = "chat_room", referencedColumnName = "room_id")
	private SharingroomsEntity chatRoom;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
	private MemberEntity member;

	@Column(name = "content", columnDefinition = "text")
	private String content;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	
}