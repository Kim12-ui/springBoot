package com.dsa.tabidabi.domain.entity.community;

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
import jakarta.persistence.OneToOne;
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
@Table(name="community_informations")
public class CommunityInfoEntity {
	// 커뮤니티_정보 고유정보
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_informations_id")
	private Integer communityInformationsId;
	
	// 커뮤니티 아이디
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private CommunityEntity community;
	
	// 여행 출발일
	@Column(name = "community_departure", length=50)
    private String communityDeparture;
	
	// 여행 복귀일
	@Column(name = "community_return", length=50)
    private String communityReturn;
	
	// 여행 대륙
	@Column(name = "community_continent", length=20)
	private String communityContinent;
	
	// 여행 국가
	@Column(name = "community_country", length=50)
	private String communityCountry;
	
	// 생성 날짜
	@CreatedDate
	@Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	
	// 수정 날짜
	@LastModifiedDate
	@Column(name = "updated_at", columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime updatedAt;
}