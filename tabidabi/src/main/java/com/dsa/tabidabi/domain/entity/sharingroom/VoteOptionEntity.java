package com.dsa.tabidabi.domain.entity.sharingroom;

import java.time.LocalDateTime;

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
@Table(name = "sharingroom_vote_options")
public class VoteOptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionId; 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_id", nullable = false)
    private SharingroomVoteEntity vote; 

    @Column(name = "option_text", nullable = false)
    private String optionText; 


}
