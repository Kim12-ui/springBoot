package net.datasa.test.domain.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 판매글 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

	// 글번호
	Integer	boardNum;

	// 작성자 아이디
	String memberId;
	
	// 카테고리
	String category;
	
	// 제목
	String title;
	
	// 본문
	String contents;
	
	// 판매가격
	Integer price;
	
	// 판매완료
	Boolean soldout;
	
	// 구매자 정보 (외래키)
	String buyerId;
	
	// 작성일
	LocalDateTime inputDate;
}
