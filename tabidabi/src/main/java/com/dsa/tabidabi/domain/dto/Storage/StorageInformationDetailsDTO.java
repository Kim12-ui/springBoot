package com.dsa.tabidabi.domain.dto.Storage;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StorageInformationDetailsDTO {
	private Integer informationDetailsId;
    private Integer storageId;
    private String planType;
    private String title;
    private String content;
    private List<String> titles; // 세부 일정 제목
    private List<String> contents; // 세부 일정 내용
    private LocalDateTime createdAt;

}