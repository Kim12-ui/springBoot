package com.dsa.tabidabi.domain.dto.sharingroom;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomApiDTO {
	
	/**
	 * -- (9) 공유방_api
create table sharingroom_api (
   sharingroom_api_id int primary key auto_increment,   -- 고유번호
   room_id int,                              -- 커뮤니티 방 번호
   foreign key (room_id) references sharingrooms(room_id)
   ON DELETE CASCADE,
   plan_type varchar(10),
   place varchar(200) not null,                  -- 여행 장소
   lat double not null,                        -- 위치1
   lng double not null                           -- 위치2
);
	 */
	
	Integer sharingroomApiId;		//고유번호
	
	Integer roomApiId;	//커뮤니티 방 번호
	
	String planType;	//일정타입 (planA,planB,planC)
	
	private List<MarkerDTO> markers;  // 마커 리스트 추가
	
//	String place;		//여행장소
//	
//	double lat;			//위도
//	
//	double lng;			//경도
	
}
