package com.dsa.tabidabi.domain.dto.Storage;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageInformationDTO {
	private Integer storageInformationId;
    private Integer storageId;
    private String planType;
    private String storageDeparture;
    private String storageReturn;
    private String storageContinent;
    private String storageCountry;
    private LocalDateTime createdAt;

}
