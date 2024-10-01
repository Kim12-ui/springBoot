package net.datasa.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.domain.dto.ReplyDTO;
import net.datasa.test.security.AuthenticatedUser;
import net.datasa.test.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 거래 게시판 Ajax 요청 처리 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("board")
public class BoardRestController {

	// 게시판 처리 서비스
	private final BoardService boardService;
	
	/**
	 * 글 목록 조회
	 * @param category 		판매상품 카테고리
	 * @param keyword  		검색어
	 * @return				게시글 목록
	 */
	@PostMapping("getList")
	public ResponseEntity<?> write(
			@RequestParam("category") String category
			, @RequestParam("keyword") String keyword
			) {
		
		log.debug("전달된 카데고리 : {}, 검색어 : {}", category, keyword);
		
		List<BoardDTO> list = boardService.getList(category, keyword);
		
		return ResponseEntity.ok(list);
	}
}
