package com.dsa.tabidabi.domain.entity.sharingroom;

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
@Table(name="sharingroom_participants")
public class SharingroomParticipantsEntity {
	
	/**
	 * desc sharingroom_participants;
create table sharingroom_participants(
   participant_id int primary key auto_increment,               -- 참여자 고유번호
   participant_member varchar(20),                           -- 멤버 아이디
   participant_room int,                                 -- 공유방 고유번호
   came_at timestamp default current_timestamp,               -- 참여시간
   FOREIGN KEY (participant_member) REFERENCES members(member_id),
    FOREIGN KEY (participant_room) REFERENCES sharingrooms(room_id)
    ON DELETE CASCADE

	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="participant_id")
	private Integer participantId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="participant_member", referencedColumnName = "member_id")
	private MemberEntity participantMember;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="participant_room", referencedColumnName = "room_id")
	private SharingroomsEntity participantRoom;
	
	@CreatedDate
	@Column(name="came_at"
			, columnDefinition = "timestamp default current_timestamp") 
	private LocalDateTime cameAt;
}
