package com.dsa.web6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.web6.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity , Integer> {

}
