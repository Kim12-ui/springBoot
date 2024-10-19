package com.dsa.tabidabi.domain.entity.sharingroom;

import java.time.LocalDateTime;

import com.dsa.tabidabi.domain.entity.MemberEntity;

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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sharingroom_vote_results")
public class VoteResultEntity {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private int resultId;

		@ManyToOne(fetch = FetchType.LAZY) 
		@JoinColumn(name = "vote_id", nullable = false)
		private SharingroomVoteEntity vote; 
		
		@ManyToOne(fetch = FetchType.LAZY) 
	    @JoinColumn(name = "option_id", nullable = false)
	    private VoteOptionEntity option; 

		@ManyToOne(fetch = FetchType.LAZY) 
	    @JoinColumn(name = "voter_id", referencedColumnName = "member_id", nullable = false)
	    private MemberEntity voter; 

		@Column(name = "voted_at", nullable = false, updatable = false)
		private LocalDateTime votedAt;

		
}

