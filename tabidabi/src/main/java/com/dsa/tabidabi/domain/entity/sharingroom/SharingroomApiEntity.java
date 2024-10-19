package com.dsa.tabidabi.domain.entity.sharingroom;



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
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "sharingroom_api")
public class SharingroomApiEntity {
	

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
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name="sharingroom_api_id")
	Integer sharingroomApiId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="room_id", referencedColumnName = "room_id")
	SharingroomsEntity roomApiId;
	
	@Column(name="plan_type", nullable=false, length = 10)
	String planType;
	
	@Column(name="place",nullable=false , length=200)
	String place;
	
	@Column(name="lat", nullable=false)
	double lat;
	
	@Column(name="lng", nullable=false)
	double lng;
	
}
