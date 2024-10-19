package com.dsa.tabidabi.domain.entity.sharingroom;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
@Table(name = "sharingroom_information_details")
public class SharingroomInformationDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="information_details_id")
	private Integer informationDetailsId;
	
	@Column(name="plan_type", length=10)
	private String planType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="information_room", referencedColumnName = "room_id")
	private SharingroomsEntity informationRoom;
	
	@Column(name="title", length=255)
	private String title;
	
	@Column(name="content", columnDefinition="text")
	private String content;
	
	@CreatedDate
	@Column(name="created_at"
			, columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name="updated_at"
			, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
	private LocalDateTime updatedAt;
	
	/**
  * create table sharingroom_information_details (
    
    information_details_id int primary key auto_increment,      -- 공유방_정보_디테일 고유번호
    
    plan_type VARCHAR(10),                              -- 계획타입
    
    information_room int,                              -- 공유방 고유정보
    foreign key (information_room) references sharingrooms(room_id) 
    ON DELETE CASCADE,
    
    title varchar(255),                                 -- 일정
    
    content text,                                    -- 상세일정 입력란
    
    created_at timestamp default current_timestamp,            -- 생성시간
    
    updated_at timestamp default current_timestamp on update current_timestamp   -- 수정시간
);

  */
}
