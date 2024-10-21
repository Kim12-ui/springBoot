package com.dsa.tabidabi.domain.dto.sharingroom;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SharingroomListDTO {
	private SharingroomDTO sharingroomDTO;
	private List<SharingroomInformationDTO> sharingroomInfoDTOList;
	private List<SharingroomParticipantsDTO> sharingroomParticipantsDTOList;
}
