package com.dsa.tabidabi.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.dsa.tabidabi.domain.dto.community.ReplyDTO;
import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityCommentsEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoDetailsEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityInfoEntity;
import com.dsa.tabidabi.domain.entity.community.CommunityLikesEntity;
import com.dsa.tabidabi.domain.entity.community.ReplyEntity;
import com.dsa.tabidabi.repository.MemberRepository;
import com.dsa.tabidabi.repository.community.CommunityCommentsRepository;
import com.dsa.tabidabi.repository.community.CommunityInfoRepository;
import com.dsa.tabidabi.repository.community.CommunityLikesRepository;
//import com.dsa.tabidabi.entity.Community;
import com.dsa.tabidabi.repository.community.CommunityRepository;
import com.dsa.tabidabi.repository.community.ReplyRepository;
import com.dsa.tabidabi.security.AuthenticatedUser;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl2 implements CommunityService2 {
	// 멤버 리포지토리
	private final MemberRepository mr;
	
    // 커뮤니티 리포지토리
 	private final CommunityRepository cr;
 	private final CommunityInfoRepository cir;
 	private final CommunityCommentsRepository ccr;
 	private final CommunityLikesRepository clr;
 	private final ReplyRepository rr;
 	
 	private static final Logger log = LoggerFactory.getLogger(CommunityServiceImpl2.class);
    
 	
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
	
	/**
	 * CommunityCommentsEntity To CommunityCommentsDTO
	 * @param CommunityCommentsEntity
	 * @return CommunityCommentsDTO
	 */
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
	
	/**
	 * CommunityListEntity To CommunityListDTO
	 * @param CommunityListEntity
	 * @return CommunityListDTO
	 */
	private CommunityListDTO convertCommunityListDTO(
			CommunityEntity communityEntity,
			CommunityInfoEntity communityInfoEntity,
			CommunityCommentsEntity communityCommentsEntity
			) {
		return CommunityListDTO.builder()
							   .communityDTO(convertCommunityDTO(communityEntity))
							   .communityInfoDTO(convertCommunityInfoDTO(communityInfoEntity))
							   .communityCommentsDTO(convertCommunityCommentsDTO(communityCommentsEntity))
							   .build();
	}

	/**
	 * 결과 값 가져오기
	 */
	@Override
	public Page<CommunityListDTO> getSearchResult(String continent, String country, String title,
			int page, int pageSize) {
		page--;
		
		// Pageable 설정 (한 페이지당 20개, 좋아요 수 내림차순 정렬)
		Pageable pageable = PageRequest.of(
				page, pageSize, Sort.by("communityId").descending());
		
		// 검색 결과에 맞는 List 출력
		Page<CommunityEntity> communiyEntityList = cr.findSearchResult(continent, country, title, pageable);
		
		// entity 데이터를 dto 데이터로 가져오기
		List<CommunityListDTO> dtoList = new ArrayList<>();
			
		// 향상된 for문으로 dtoList채우기
		for (CommunityEntity communityEntity : communiyEntityList) {
			// CommunityInfoEntity & CommunityCommentsEntity 인스턴스 생성
			CommunityInfoEntity infoEntity = cir.findById(communityEntity.getCommunityInfoEntity().getCommunityInformationsId())
					.orElseThrow(() -> new EntityNotFoundException("해당 게시글은 없습니다."));
			CommunityCommentsEntity commentsEntity = ccr.findById(communityEntity.getCommunityCommentsEntity().getCommunityCommentsId())
					.orElseThrow(() -> new EntityNotFoundException("해당 커멘드는 없습니다."));
			
			// CommunityListDTO 리스트 +1
			CommunityListDTO dto = convertCommunityListDTO(communityEntity, infoEntity, commentsEntity);
			dtoList.add(dto);
		}
		
		Page<CommunityListDTO> communityListDTOPage
		= new PageImpl<>(dtoList, pageable, communiyEntityList.getTotalElements());
		
		return communityListDTOPage;
	}

	/**
	 * 좋아요 테이블에 정보 저장후 결과값 출력
	 */
	@Override
	public Boolean like(Integer communityId, AuthenticatedUser user) {
		// CommunityEntity 인스턴스 생성
		CommunityEntity communityEntity = cr.findById(communityId)
				.orElseThrow(() -> new EntityNotFoundException("없는 게시판입니다."));
		
		// MemberEntity 인스턴스 생성
		MemberEntity memberEntity = mr.findById(user.getId())
				.orElseThrow(() -> new EntityNotFoundException("없는 아이디입니다."));
		
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
			// 좋아요 수 1 감소
			communityEntity.setLikeCount(communityEntity.getLikeCount()-1);
			
			// 테이블에 값 삭제
			clr.delete(checkCommunityEntity);
			
			// false 값 반환
			return false;
		}
	}
	
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

	
	@Override
	public Boolean selectlike(Integer communityId, AuthenticatedUser user) {
		// CommunityEntity 인스턴스 생성
		CommunityEntity communityEntity = cr.findById(communityId)
				.orElseThrow(() -> new EntityNotFoundException("없는 게시판입니다."));
		
		// MemberEntity 인스턴스 생성
		MemberEntity memberEntity = mr.findById(user.getId())
				.orElseThrow(() -> new EntityNotFoundException("없는 아이디입니다."));
		
		// communityId => int communityId
		// memberId => AuthenticatedUser user
		CommunityLikesEntity checkCommunityEntity 
		= clr.findByCommunityAndMember(communityEntity, memberEntity);
		
		if (checkCommunityEntity != null) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public List<CommunityInfoDetailsDTO> getPostsBycommunityId(Integer communityId) {
		CommunityEntity communityEntity = cr.findById(communityId)
				.orElseThrow(() -> new EntityNotFoundException("해당 커뮤니티 게시판은 없습니다."));
		List<CommunityInfoDetailsEntity> cide = communityEntity.getCommunityInfoEdtailsList();
		
		List<CommunityInfoDetailsDTO> dtoList = new ArrayList<>();
		for (CommunityInfoDetailsEntity entity : cide) {
			CommunityInfoDetailsDTO dto = convertCommunityInfoDetailsDTO(entity);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	

	/**
	 * 특정 회원이 작성한 커뮤니티 목록 가져오기
	 * @param memberId 회원 ID
	 * @return CommunityDTO 리스트
	 */
	@Override
	public List<CommunityDTO> getCommunitiesByMember(String memberId) {
	    log.debug("Member ID: {}", memberId); // 추가: 현재 회원 ID 로그
	    List<CommunityEntity> communities = cr.findByMember_MemberId(memberId);
	    log.debug("Communities: {}", communities); // 추가: 반환되는 커뮤니티 리스트 로그
	    List<CommunityDTO> communityDTOs = new ArrayList<>();
	    
	    for (CommunityEntity community : communities) {
	        communityDTOs.add(convertCommunityDTO(community));
	    }
	    
	    return communityDTOs;
	}

	@Override
	public CommunityEntity findById(Integer communityId) {
		 return cr.findById(communityId).orElseThrow(() -> new EntityNotFoundException("해당 엔티티는 없습니다."));
	}

	/**
	 * 댓글 작성
	 * @param communityId 특정 커뮤니티 게시판
	 * @param replyContent 댓글 내용
	 * @param memberId 댓글 작성자
	 */
	@Override
	public void replyWrite(int communityId, String replyContent, String memberId) {
		CommunityEntity communityEntity = cr.findById(communityId)
				.orElseThrow(() -> new EntityNotFoundException("해당 게시글이 없습니다."));
		
		MemberEntity memberEntity = mr.findById(memberId)
				.orElseThrow(() -> new EntityNotFoundException("해당 회원이 없습니다."));
		
		ReplyEntity replyEntity = ReplyEntity.builder()
								.community(communityEntity)
								.communityMember(communityEntity.getMember())
								.replyMember(memberEntity)
								.replyContent(replyContent)
								.build();
		
		rr.save(replyEntity);
	}

	/**
	 * 댓글 리스트 조회
	 * @param communityId 커뮤니티 게시판 번호
	 * @return 리플 목록
	 */
	@Override
	public List<ReplyDTO> getReplyList(int communityId) {
		Sort sort = Sort.by(Sort.Direction.ASC, "communityReplyId");
		List<ReplyEntity> replyEntityList = rr.findByCommunity_CommunityId(communityId,sort);
		List<ReplyDTO> replyDTOList = new ArrayList<ReplyDTO>();
		
		for (ReplyEntity entity : replyEntityList) {
			ReplyDTO dto = ReplyDTO.builder()
						.communityReplyId(entity.getCommunityReplyId())
						.communityId(entity.getCommunity().getCommunityId())
						.communityMemberId(entity.getCommunityMember().getNickname())
						.replyMemberNickname(entity.getReplyMember().getNickname())
						.replyMemberId(entity.getReplyMember().getMemberId())
						.replyContent(entity.getReplyContent())
						.createdAt(entity.getCreatedAt())
						.build();
			replyDTOList.add(dto);
		}
		
		return replyDTOList;
	}

	/**
	 * 댓글 삭제
	 * @param communityReplyId
	 * @param communityId
	 * @param user
	 * @return 댓글 삭제
	 */
	@Override
	public void replyDelete(int communityReplyId, int communityId, AuthenticatedUser user) {
		ReplyEntity replyEntity = rr.findById(communityReplyId)
				.orElseThrow(() -> new EntityNotFoundException("리플 정보가 없습니다."));
		
		if (!user.getId().equals(replyEntity.getReplyMember().getMemberId())) {
			throw new RuntimeException("삭제 권한이 없습니다.");
		}
		
		rr.delete(replyEntity);
	}

	// 다음 버튼 클릭시 (다음 게시물로 이동)
	@Override
	public Integer findNextCommunityId(Integer communityId) {
		return cr.findFirstByCommunityIdGreaterThanOrderByCommunityIdAsc(communityId);
	}

	// 이전 버튼 클릭시 (이전 게시물로 이동)
	@Override
	public Integer findPreviousCommunityId(Integer communityId) {
		return cr.findFirstByCommunityIdLessThanOrderByCommunityIdDesc(communityId);
	}
	
}
