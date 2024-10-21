package com.dsa.tabidabi.service;

import java.util.List;

import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomListDTO;

public interface SharingroomService {

	List<SharingroomListDTO> findLeaderDTOList(String memberId);

	List<SharingroomListDTO> findParticipantDTOList(String memberId);

}
