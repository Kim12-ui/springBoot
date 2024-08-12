package com.dsa.web3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.web3.entity.MemberEntity;

/*
 * member13 테이블 관련 쿼리 실행 메서드 정의
 * */
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

}