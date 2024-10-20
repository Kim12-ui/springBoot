package com.dsa.tabidabi.repository.community;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dsa.tabidabi.domain.dto.community.CommunityDTO;
import com.dsa.tabidabi.domain.entity.community.CommunityEntity;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {
	
	// 좋아요 수 100위를 기준으로 인기 패키지에 출력 (+좋아요 순으로 배열)
	List<CommunityEntity> findTop100ByOrderByLikeCountDesc(Sort sort);
	
	// 검색창 결과
	@Query("SELECT c FROM CommunityEntity c JOIN c.communityInfoEntity ci WHERE " +
		       "(:continent = '' OR ci.communityContinent = :continent) AND " +
		       "(:country = '' OR ci.communityCountry = :country) AND " +
		       "(c.title LIKE %:title%)")
		List<CommunityEntity> findPopularSearchResult(
		    @Param("continent") String continent, 
		    @Param("country") String country, 
		    @Param("title") String title);
	
		// 검색창 결과
		@Query("SELECT c FROM CommunityEntity c JOIN c.communityInfoEntity ci WHERE " +
			       "(:continent = '' OR ci.communityContinent = :continent) AND " +
			       "(:country = '' OR ci.communityCountry = :country) AND " +
			       "(c.title LIKE %:title%)")
			Page<CommunityEntity> findSearchResult(
			    @Param("continent") String continent, 
			    @Param("country") String country, 
			    @Param("title") String title,
			    Pageable pageable);

	// 검색창 결과 (페이징)
	@Query("SELECT c FROM CommunityEntity c JOIN c.communityInfoEntity ci WHERE " +
		       "(:continent = '' OR ci.communityContinent = :continent) AND " +
		       "(:country = '' OR ci.communityCountry = :country) AND " +
		       "(c.title LIKE %:title%)")
	Page<CommunityEntity> findPopularSearchResult(
			@Param("continent") String continent, 
		    @Param("country") String country, 
		    @Param("title") String title,
		    Pageable pageable
			);

	List<CommunityEntity> findByMember_MemberId(String memberId);

	@Query(value = "SELECT community_id FROM communities WHERE community_id > :communityId ORDER BY community_id ASC LIMIT 1", nativeQuery = true)
    Integer findFirstByCommunityIdGreaterThanOrderByCommunityIdAsc(@Param("communityId") Integer communityId);

    @Query(value = "SELECT community_id FROM communities WHERE community_id < :communityId ORDER BY community_id DESC LIMIT 1", nativeQuery = true)
    Integer findFirstByCommunityIdLessThanOrderByCommunityIdDesc(@Param("communityId") Integer communityId);

}
