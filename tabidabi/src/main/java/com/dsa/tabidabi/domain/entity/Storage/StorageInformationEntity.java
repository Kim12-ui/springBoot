package com.dsa.tabidabi.domain.entity.Storage;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.dsa.tabidabi.domain.entity.MemberEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "storage_information")
public class StorageInformationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_information_id", nullable = false)
    private Integer storageInformationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "storage_id", referencedColumnName = "storage_id")
    private StorageEntity storage;

    @Column(name = "plan_type", length = 10)
    private String planType;

    @Column(name = "storage_departure", length = 50)
    private String storageDeparture;

    @Column(name = "storage_return", length = 50)
    private String storageReturn;

    @Column(name = "storage_continent", length = 20)
    private String storageContinent;

    @Column(name = "storage_country", length = 50)
    private String storageCountry;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
