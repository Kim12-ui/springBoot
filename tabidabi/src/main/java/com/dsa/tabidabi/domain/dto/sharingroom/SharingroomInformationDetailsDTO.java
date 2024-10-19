package com.dsa.tabidabi.domain.dto.sharingroom;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomInformationDetailsDTO {
	
	private Integer informationDetailsId;		//공유방 정보 디테일 고유번호
	
	private String planType;					//계획타입
	
	private Integer informationRoom;			//공유방고유정보
	
	private String title;						//일정
	
	private String content;		//상세 일정 입력란
	
	private List<String> titles; // 세부 일정 제목
    private List<String> contents; // 세부 일정 내용
	
	private LocalDateTime createdAt;			//생성시간
	
	private LocalDateTime updatedAt;			//수정시간
	/**
	 *  information_details_id int primary key auto_increment,      -- 공유방_정보_디테일 고유번호
    plan_type VARCHAR(10),                              -- 계획타입
    information_room int,                              -- 공유방 고유정보
    foreign key (information_room) references sharingrooms(room_id) 
    ON DELETE CASCADE,
    title varchar(255),                                 -- 일정
    content text,                                    -- 상세일정 입력란
    created_at timestamp default current_timestamp,            -- 생성시간
    updated_at timestamp default current_timestamp on update current_timestamp   -- 수정시간

	 */
}
