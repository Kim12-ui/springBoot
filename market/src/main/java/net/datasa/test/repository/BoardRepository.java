package net.datasa.test.repository;

import net.datasa.test.domain.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 관련 repository
 */

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	// 전달된 category와 keyword를 기반으로 게시글을 조회하는 메소드
    @Query("""
        SELECT b FROM BoardEntity b 
        WHERE 
            b.category like %:category% 
            AND (b.title LIKE %:keyword% OR b.contents LIKE %:keyword%)
        ORDER BY
            b.boardNum DESC
        """)
    List<BoardEntity> selectBoardList(
            @Param("category") String category, @Param("keyword") String keyword);

}
