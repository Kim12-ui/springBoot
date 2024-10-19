package com.dsa.tabidabi.domain.dto.sharingroom;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomParticipantsDTO {
	
	private Integer participantId;		// 참여자 고유번호
	
	private String participantMember;	// 멤버 아이디
	
	private Integer participantRoom;		// 공유방 고유번호
	
	private LocalDateTime cameAt;		// 참여시간
	
	/**
	 * participant_id int primary key auto_increment,               -- 참여자 고유번호
   participant_member varchar(20),                           -- 멤버 아이디
   participant_room int,                                 -- 공유방 고유번호
   came_at timestamp default current_timestamp,               -- 참여시간

	 */
}
