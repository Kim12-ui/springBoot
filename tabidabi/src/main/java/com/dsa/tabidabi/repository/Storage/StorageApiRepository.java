package com.dsa.tabidabi.repository.Storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.entity.Storage.StorageApiEntity;

@Repository
public interface StorageApiRepository extends JpaRepository<StorageApiEntity, Integer> {

}
