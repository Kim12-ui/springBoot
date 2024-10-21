package com.dsa.tabidabi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomInformationDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomListDTO;
import com.dsa.tabidabi.domain.dto.sharingroom.SharingroomParticipantsDTO;
import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomInformationEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomParticipantsEntity;
import com.dsa.tabidabi.domain.entity.sharingroom.SharingroomsEntity;
import com.dsa.tabidabi.repository.MemberRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomInformationRepository;
import com.dsa.tabidabi.repository.sharingroom.SharingroomRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SharingroomServiceImpl implements SharingroomService {
	
	private final MemberRepository mr;
	
	private final SharingroomRepository sr;
	private final SharingroomInformationRepository sir;
	
	/**
	 * SharingroomsEntity to SharingroomDTO
	 * @param SharingroomsEntity
	 * @return SharingroomDTO
	 */
	private SharingroomDTO convertSharingroomDTO(SharingroomsEntity entity) {
		return SharingroomDTO.builder()
				.roomId(entity.getRoomId())
				.roomChief(entity.getRoomChief().getMemberId())
				.roomTitle(entity.getRoomTitle())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
	
	/**
	 * SharingroomInformationDTO to SharingroomInformationEntity
	 * @param SharingroomInformationEntity
	 * @return SharingroomInformationDTO
	 */
	private SharingroomInformationDTO convertSharingroomInformationDTO 
	(SharingroomInformationEntity entity) {
		return SharingroomInformationDTO.builder()
				.informationId(entity.getInformationId())
				.informationRoom(entity.getInformationRoom().getRoomId())
				.planType(entity.getPlanType())
				.informationDeparture(entity.getInformationDeparture())
				.informationReturn(entity.getInformationReturn())
				.informationContinent(entity.getInformationContinent())
				.informationCountry(entity.getInformationCountry())
				.informationBudget(entity.getInformationBudget())
				.informationBudgetPeople(entity.getInformationBudgetPeople())
				.build();
	}
	
	private List<SharingroomInformationDTO> convertSharingroomInformationDTOList
	(SharingroomsEntity sharingroomsEntity) {
		List<SharingroomInformationEntity> entityList
		= sharingroomsEntity.getSharingroomInformationEntity();
		
		List<SharingroomInformationDTO> dtoList = new ArrayList<>();
		
		for (SharingroomInformationEntity entity : entityList) {
			SharingroomInformationDTO dto = convertSharingroomInformationDTO(entity);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	/**
	 * SharingroomParticipantsDTO to SharingroomParticipantsEntity
	 * @param SharingroomParticipantsEntity
	 * @return SharingroomParticipantsDTO
	 */
	private SharingroomParticipantsDTO convertSharingroomParticipantsDTO
	(SharingroomParticipantsEntity entity) {
		return SharingroomParticipantsDTO.builder()
				.participantId(entity.getParticipantId())
				.participantMember(entity.getParticipantMember().getMemberId())
				.participantRoom(entity.getParticipantRoom().getRoomId())
				.cameAt(entity.getCameAt())
				.build();
	}
	
	private List<SharingroomParticipantsDTO> convertSharingroomParticipantsDTOList(
			SharingroomsEntity sharingroomsEntity
			) {
		List<SharingroomParticipantsEntity> entityList =
		sharingroomsEntity.getSharingroomParticipantsEntityList();
		
		List<SharingroomParticipantsDTO> dtoList = new ArrayList<>();
		
		for (SharingroomParticipantsEntity entity : entityList) {
			SharingroomParticipantsDTO dto = convertSharingroomParticipantsDTO(entity);
			dtoList.add(dto);
		}		
		return dtoList;
	}
	
	/**
	 * convertSharingroomListDTO
	 * @param SharingroomsEntity
	 * @param SharingroomInformationEntity
	 * @return SharingroomListDTO
	 */
	private SharingroomListDTO convertSharingroomListDTO(
			SharingroomsEntity entity
			) {
		return SharingroomListDTO.builder()
				.sharingroomDTO(convertSharingroomDTO(entity))
				.sharingroomInfoDTOList(convertSharingroomInformationDTOList(entity))
				.sharingroomParticipantsDTOList(convertSharingroomParticipantsDTOList(entity))
				.build();
	}
	
	/**
	 * 본인 = 방장인 공유방 리스트 출력
	 * @param memberId 현재 접속한 유저 아이디
	 * @param 
	 */
	@Override
	public List<SharingroomListDTO> findLeaderDTOList(String memberId) {
		MemberEntity memberEntity = mr.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("해당 유저는 없습니다."));
		
		List<SharingroomsEntity> sharingroomEntityList = sr.findByRoomChief(memberEntity);
		
		List<SharingroomListDTO> leaderDTOList = new ArrayList<>();
		
		for (SharingroomsEntity sharingroomEntity : sharingroomEntityList) {
			SharingroomListDTO dto = convertSharingroomListDTO(sharingroomEntity);
			leaderDTOList.add(dto);
		}
		
		return leaderDTOList;
	}

	/**
	 * 본인 = 참가자인 공유방 리스트 출력
	 */
	@Override
	public List<SharingroomListDTO> findParticipantDTOList(String memberId) {
		
		List<SharingroomsEntity> sharingroomEntityList = sr.findRoomsWhereUserIsParticipantNotLeader(memberId);
		
		List<SharingroomListDTO> participantDTOList = new ArrayList<>();
		
		for (SharingroomsEntity entity : sharingroomEntityList) {
			SharingroomListDTO dto = convertSharingroomListDTO(entity);
			participantDTOList.add(dto);
		}
		
		return participantDTOList;
	}
	
}
