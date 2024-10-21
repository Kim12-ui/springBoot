package com.dsa.tabidabi.domain.entity.sharingroom;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dsa.tabidabi.domain.entity.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="sharingrooms")
public class SharingroomsEntity {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="room_id")
	private Integer roomId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="room_chief", referencedColumnName = "member_id")
	private MemberEntity roomChief;
	
	@Column(name="room_title", length = 200 )
	private String roomTitle;
	
	@CreatedDate
	@Column(name = "created_at"
			, columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "updated_at"
			, columnDefinition = "timestamp default current_timestamp")
	private LocalDateTime updatedAt;

	// 참가자 목록
    @OneToMany(mappedBy = "participantRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharingroomParticipantsEntity> SharingroomParticipantsEntityList;
    
    @OneToMany(mappedBy = "informationRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharingroomInformationEntity> sharingroomInformationEntity;
}
