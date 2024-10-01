package com.dsa.web6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.web6.entity.MemberEntity;

/**
 * 회원 정보 Repository
 */

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}
