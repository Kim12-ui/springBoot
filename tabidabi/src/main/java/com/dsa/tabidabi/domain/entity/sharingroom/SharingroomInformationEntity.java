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
@Table(name = "sharingroom_information")
public class SharingroomInformationEntity {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name="information_id")
	private Integer informationId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="information_room", referencedColumnName = "room_id")
	private SharingroomsEntity informationRoom;
	
	@Column(name="plan_type", length=10)
	private String planType;
	
	@Column(name="information_departure", length=50)
	private String informationDeparture;
	
	@Column(name="information_return", length=50)
	private String informationReturn;
	
	@Column(name="information_continent", length=20)
	private String informationContinent;
	
	@Column(name="information_country")
	private String informationCountry;
	
	@Column(name="information_budget")
	private Integer informationBudget;
	
	@Column(name="information_budget_people")
	private Integer informationBudgetPeople;
	
	@CreatedDate
	@Column(name="created_at"
			, columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name="updated_at"
			, columnDefinition = "timestamp default current_timestamp on update current_timestamp")
	private LocalDateTime updatedAt;
	/**
	 * create table sharingroom_information(
    information_id int primary key auto_increment,               -- 공유방_정보 고유번호
    
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
);

	 */
}
