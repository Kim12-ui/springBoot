package com.dsa.tabidabi.domain.entity.community;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Entity
@Table(name="community_api")
public class CommunityApiEntity {
	// 고유번호
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "community_api_id")
	private Integer CommunityApiId;
	
	// 커뮤니티 아이디
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private CommunityEntity community;
	
	// 장소 이름
	@Column(name = "place", length=200, nullable=false)
	private String place;
	
	// 위치1 (위도)
	@Column(name = "lat", nullable = false)
    private Double lat;

	// 위치2 (경도)
    @Column(name = "lng", nullable = false)
    private Double lng; 
}
