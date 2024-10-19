package com.dsa.tabidabi.service.home;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dsa.tabidabi.domain.dto.community.CommunityCommentsDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityInfoDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityInfoDetailsDTO;
import com.dsa.tabidabi.domain.dto.community.CommunityListDTO;
import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityCommentsEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoDetailsEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityLikesEntity;
import com.dsa.tabidabi.repository.MemberRepository;
import com.dsa.tabidabi.repository.community.CommunityCommentsRepository;
import com.dsa.tabidabi.repository.community.CommunityInfoDetailsRepository;
import com.dsa.tabidabi.repository.community.CommunityInfoRepository;
import com.dsa.tabidabi.repository.community.CommunityLikesRepository;
import com.dsa.tabidabi.repository.community.CommunityRepository;
import com.dsa.tabidabi.security.AuthenticatedUser;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class HomeServiceImpl implements HomeService {
	
	// 커뮤니티 리포지토리
	private final CommunityRepository cr;
	private final CommunityInfoRepository cir;
	private final CommunityCommentsRepository ccr;
	private final CommunityInfoDetailsRepository cidr;
	private final CommunityLikesRepository clr;
	
	// 멤버 레포지토리
	private final MemberRepository mr;
	
	/**
	 * From CommunityEntity To CommunityDTO
	 * @param CommunityEntity
	 * @return CommunityDTO
	 */
	private CommunityDTO convertCommunityDTO(CommunityEntity entity) {
		return CommunityDTO.builder()
			   .communityId(entity.getCommunityId())
			   .memberId(entity.getMember().getMemberId())
			   .nickname(entity.getMember().getNickname())
			   .title(entity.getTitle())
			   .likeCount(entity.getLikeCount())
			   .viewCount(entity.getViewCount())
			   .createdAt(entity.getCreatedAt())
			   .updatedAt(entity.getUpdatedAt())
			   .build();
	}
	
	/**
	 * From CommunityInfoDTO To CommunityInfoEntity
	 * @param CommunityInfoDTO
	 * @return CommunityInfoEntity
	 */
	private CommunityInfoDTO convertCommunityInfoDTO 
	(CommunityInfoEntity entity) {
		return CommunityInfoDTO.builder()
				.communityInformationsId(entity.getCommunityInformationsId())
				.communityId(entity.getCommunity().getCommunityId())
				.communityDeparture(entity.getCommunityDeparture())
				.communityReturn(entity.getCommunityReturn())
				.communityContinent(entity.getCommunityContinent())
				.communityCountry(entity.getCommunityCountry())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
	
	/**
	 * From CommunityInfoDetailsEntity To CommunityInfoDetailsDTO
	 * @param CommunityInfoDetailsEntity
	 * @return CommunityInfoDetailsDTO
	 */
	private CommunityInfoDetailsDTO convertCommunityInfoDetailsDTO
	(CommunityInfoDetailsEntity entity) {
		return CommunityInfoDetailsDTO.builder()
				.communityDetailsId(entity.getCommunityDetailsId())
				.communityId(entity.getCommunity().getCommunityId())
				.title(entity.getTitle())
				.content(entity.getContent())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
	
	private CommunityCommentsDTO convertCommunityCommentsDTO
	(CommunityCommentsEntity entity) {
		return CommunityCommentsDTO.builder()
				.communityCommentsId(entity.getCommunityCommentsId())
				.communityId(entity.getCommunity().getCommunityId())
				.content(entity.getContent())
				.representativeImage(entity.getRepresentativeImage())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
	
	private CommunityListDTO convertToCommunityListDTO(CommunityEntity entity) {
	    CommunityDTO communityDTO = convertCommunityDTO(entity);
	    CommunityInfoDTO communityInfoDTO = convertCommunityInfoDTO(entity.getCommunityInfoEntity());
	    CommunityCommentsDTO communityCommentsDTO = convertCommunityCommentsDTO(entity.getCommunityCommentsEntity());
	    
	    return CommunityListDTO.builder()
	    		.communityDTO(communityDTO)
	    		.communityInfoDTO(communityInfoDTO)
	    		.communityCommentsDTO(communityCommentsDTO)
	    		.build();
	}

	/**
	 * 페이징 처리
	 */
	@Override
	public Page<CommunityListDTO> getPopularSearchResult(String continent, String country, String title, int page,
			int pageSize) {
		page--;	// 페이지를 0-based index로 변환
		
		// Pageable 설정 (한 페이지당 20개, 좋아요 수 내림차순 정렬)
		Pageable pageable = PageRequest.of(
				page, pageSize, Sort.by("likeCount").descending());
		
		Page<CommunityEntity> communityEntityPage 
		= cr.findPopularSearchResult(continent,country,title,pageable);
		
		// 전체 결과가 100개를 초과할 경우, 100개로 제한
	    int totalResults = Math.min((int) communityEntityPage.getTotalElements(), 100);
	    
	    // DTO 리스트 초기화
	    List<CommunityListDTO> communityListDTOs = new ArrayList<>();
	    
	    // 페이지 처리 및 DTO 변환
	    for (int i = 0; i <Math.min(communityEntityPage.getContent().size(), totalResults); i++) {
	    	CommunityEntity communityEntity = communityEntityPage.getContent().get(i);
	    	
	    	// CommunityListDTO로 변환
	    	CommunityListDTO communityListDTO = convertToCommunityListDTO(communityEntity);
	        communityListDTOs.add(communityListDTO);
	    }
	    // Page<CommunityListDTO> 생성
	    Page<CommunityListDTO> communityListDTOPage 
	    = new PageImpl<>(communityListDTOs, pageable, totalResults);
	    
		return communityListDTOPage;
	}
	
	/**
	 * 해당 인증된 유저의 좋아요 눌렀던 커뮤니티 목록 출력
	 * @param memberId 인증된 유저의 아이디
	 */
	@Override
	public List<CommunityDTO> selectLikedList(String memberId) {
		MemberEntity memberEntity = mr.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("존재하지 않는 아이디입니다."));
		
		List<CommunityEntity> communityEntitys =  clr.findByMember(memberEntity);
		
		List<CommunityDTO> communityDTOs = new ArrayList<>();
		
		for (CommunityEntity entity : communityEntitys) {
			CommunityDTO dto = convertCommunityDTO(entity);
			communityDTOs.add(dto);
		}
		
		return communityDTOs;
	}
	
	/**
	 * 좋아요 생성 / 삭제
	 * true : 좋아요 생성 (community_like에 정보 추가, insert)
	 * false : 좋아요 삭제 (community_like에 정보 삭제, delete)
	 * @param communityId
	 * @param user 
	 */
	@Override
	public Boolean like(Integer communityId, AuthenticatedUser user) {
		// CommunityEntity 인스턴스 생성
		CommunityEntity communityEntity = cr.findById(communityId)
				.orElseThrow(() -> new EntityNotFoundException("없는 커뮤니티 게시판입니다."));
				
		// MemberEntity 인스턴스 생성
		MemberEntity memberEntity = mr.findById(user.getId())
				.orElseThrow(() -> new EntityNotFoundException("없는 멤버입니다."));
		
		// communityId => int communityId
		// memberId => AuthenticatedUser user
		CommunityLikesEntity checkCommunityEntity 
		= clr.findByCommunityAndMember(communityEntity, memberEntity);
		
		// 만약에 좋아요가 눌려지지 않았던 상황이라면?
		if (checkCommunityEntity == null) {
			// 멤버 값이랑 커뮤니티 값 저장
			CommunityLikesEntity communityLikesEntity = new CommunityLikesEntity();
			communityLikesEntity.setCommunity(communityEntity);
			communityLikesEntity.setMember(memberEntity);
			
			// 좋아요 수 1 증가
			communityEntity.setLikeCount(communityEntity.getLikeCount()+1);
			
			// 테이블에 값 저장
			clr.save(communityLikesEntity);
			
			// true 값 반환
			return true;
		} 
		// 만약에 좋아요가 눌려져 있는 상황이라면?
		else {
			CommunityLikesEntity communityLikesEntity
			= clr.findByCommunityAndMember(communityEntity, memberEntity);
			
			// 좋아요 수 1 감소
			communityEntity.setLikeCount(communityEntity.getLikeCount()-1);
			
			// 테이블에 값 삭제
			clr.delete(communityLikesEntity);
			
			// false 값 반환
			return false;
		}
		
	}
	
}
