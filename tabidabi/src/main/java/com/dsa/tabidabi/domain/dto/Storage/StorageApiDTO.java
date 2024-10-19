package com.dsa.tabidabi.domain.dto.Storage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StorageApiDTO {
    private Integer storageApiId;
    private Integer storageId;
    private String planType;
    private String place;
    private Double lat;
    private Double lng;
}
