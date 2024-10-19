package com.dsa.tabidabi.domain.entity.sharingroom;

import java.time.LocalDateTime;

import com.dsa.tabidabi.domain.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sharingroom_votes")
public class SharingroomVoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voteId; 

    @ManyToOne
    @JoinColumn(name = "vote_room", referencedColumnName = "room_id", nullable = false)
    private SharingroomsEntity voteRoom; 

    @ManyToOne
    @JoinColumn(name = "vote_creator", referencedColumnName = "member_id", nullable = false)
    private MemberEntity voteCreator; 

    @Column(name = "vote_title", nullable = false)
    private String voteTitle; 

    @Column(name = "vote_end_date", nullable = false)
    private LocalDateTime voteEndDate; 

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt; 
}
