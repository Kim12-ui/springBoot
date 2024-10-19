package com.dsa.tabidabi.service.sharingroom;

import java.util.Map;

import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomApiDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDetailsDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomParticipantsDTO;

public interface SharingRoomService {

	Map<String, Integer> createSharingRoom(String roomChief);

    void saveSharingRoom(SharingroomApiDTO sharingroomApi, 
                         SharingroomDTO sharingroom,
                         SharingroomInformationDTO sharingroomInformation,
                         SharingroomInformationDetailsDTO sharingroomInformationDetails,
                         SharingroomParticipantsDTO sharingroomParticipants);
}
