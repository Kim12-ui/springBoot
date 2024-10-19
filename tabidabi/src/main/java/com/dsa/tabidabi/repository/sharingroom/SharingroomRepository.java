package com.dsa.tabidabi.repository.sharingroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomsEntity;

@Repository
public interface SharingroomRepository extends JpaRepository<SharingroomsEntity,Integer> {
	

}
