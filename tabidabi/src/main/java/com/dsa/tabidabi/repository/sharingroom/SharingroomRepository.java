package com.dsa.tabidabi.repository.sharingroom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomsEntity;

@Repository
public interface SharingroomRepository extends JpaRepository<SharingroomsEntity,Integer> {

	List<SharingroomsEntity> findByRoomChief(MemberEntity member);

	@Query("SELECT sr FROM SharingroomsEntity sr JOIN sr.SharingroomParticipantsEntityList sp " +
	           "WHERE sp.participantMember.memberId = :memberId " +
	           "AND sr.roomChief.memberId != :memberId")
	    List<SharingroomsEntity> findRoomsWhereUserIsParticipantNotLeader(@Param("memberId") String memberId);

	
}
