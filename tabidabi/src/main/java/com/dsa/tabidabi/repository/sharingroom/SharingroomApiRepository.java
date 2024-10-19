package com.dsa.tabidabi.repository.sharingroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomApiEntity;

@Repository
public interface SharingroomApiRepository extends JpaRepository<SharingroomApiEntity,Integer> {

}
