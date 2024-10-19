package com.dsa.tabidabi.domain.dto.sharingroom;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarkerDTO {
	private String place;  // 여행 장소
    private double lat;    // 위도
    private double lng;    // 경도
}
