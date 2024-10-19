package com.dsa.tabidabi.domain.dto.sharingroom;


import java.time.LocalDateTime;

import com.dsa.tabidabi.domain.dto.MemberDTO;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomDTO {
	
	
	private Integer roomId;		//공유방 고유번호
	
	private String roomChief;	//방장
	
	private String roomTitle;	//방 제목
	
	private LocalDateTime createdAt; //방 생성일
	
	private LocalDateTime updatedAt; //정보 수정(저장하기)
	/**
	 * create table sharingrooms (
   room_id int primary key auto_increment,               -- 공유방 고유번호
   room_chief varchar(20),                           -- 방장
   room_title varchar(200),                        -- 방 제목
   created_at timestamp default current_timestamp,         -- 방 생성일
   updated_at timestamp default current_timestamp          -- 정보 수정 (저장하기)
   on update current_timestamp,
   foreign key (room_chief) references members(member_id)

	 */
	
	
	
}
