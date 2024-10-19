package com.dsa.tabidabi.domain.dto.sharingroom;

import java.time.LocalDateTime;

import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomsEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomInformationDTO {
	
	private Integer informationId;		//공유방정보고유번호
	
	private Integer informationRoom;	//공유방 고유번호
	
	private String planType;			//planA, planB, planC
	
	private String informationDeparture;	// 여행 출발일
	
	private String informationReturn;		// 여행 복귀일
	
	private String informationContinent;	// 여행 대륙
	
	private String informationCountry;		// 여행 나라
	
	private Integer informationBudget;		// 여행 총경비
	
	private Integer informationBudgetPeople;	//더치페이할 인원 수
	
	private LocalDateTime createdAt;		//생성시간
	
	private LocalDateTime updatedAt;		// 수정시간
	/**
	 * information_id int primary key auto_increment,               -- 공유방_정보 고유번호
    information_room int,                                 -- 공유방 고유 번호
    foreign key (information_room) references sharingrooms(room_id) 
    ON DELETE CASCADE,
    plan_type VARCHAR(10),                                  -- planA, planB, planC
    information_departure varchar(50),                        -- 여행 출발일
    information_return varchar(50),                           -- 여행 복귀일
    information_continent varchar(20),                        -- 여행 대륙
    information_country varchar(50),                        -- 여행 나라
    information_budget int,                                 -- 여행 총경비   
    information_budget_people int,                           -- 더치페이할 인원수
    created_at timestamp default current_timestamp,               -- 생성시간
    updated_at timestamp default current_timestamp on update current_timestamp   -- 수정시간

	 */
}
