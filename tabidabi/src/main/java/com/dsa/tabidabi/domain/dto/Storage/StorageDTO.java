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
public class StorageDTO {
    private Integer storageId;
    private String memberId;
    private Integer roomId;
    private String roomTitle;
    private LocalDateTime createdAt;
}
