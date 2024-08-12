package com.dsa.web4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.web4.entity.GuestBookEntity;

@Repository
public interface GuestRepository extends JpaRepository<GuestBookEntity,Integer> {

}