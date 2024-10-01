package net.datasa.test.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.test.domain.dto.BoardDTO;
import net.datasa.test.security.AuthenticatedUser;
import net.datasa.test.service.BoardService;

/**
 * 거래 게시판 관련 콘트롤러
 */

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("board")
public class BoardController {

	//게시판 처리 서비스
    private final BoardService boardService;

    /**
     * 글목록으로 이동
     * @return 글목록 HTML 파일
     */
    @GetMapping("list")
    public String list() {
        return "board/list";
    }
    
    /**
     * 글쓰기 폼으로 이동
     * @return 글쓰기폼 HTML 파일
     */
    @GetMapping("write")
    public String write() {
    	return "board/writeForm";
    }
    
    /**
     * 글 저장
     * @param board	사용자가 입력한 거래 글정보
     * @return 		글목록 경로
     */
    @PostMapping("write")
    public String write(
    		@ModelAttribute BoardDTO board,
    		@AuthenticationPrincipal AuthenticatedUser user
    		) {
    	board.setMemberId(user.getUsername());
    	log.debug("전달된 글정보 : {}", board);
    	boardService.write(board);
    	
    	return "redirect:list";
    }
    
    /**
     * 글 읽기
     * @param boardNum 조회할 글번호
     * @param model
     * @return 			글 읽기 HTML 파일
     */
    @GetMapping("read")
    public String read(
    		@RequestParam("boardNum") int boardNum,
    		Model model
    		) {
    	
    	log.debug("조회할 글번호 : {}", boardNum);
    	BoardDTO boardDTO = boardService.getBoard(boardNum);
    	
    	model.addAttribute("board",boardDTO);
    	return "board/read";
    }
    
    /**
     * 판매글 삭제
     * @param boardNum 삭제할 글 번호
     * @param user	   사용자 정보
     * @return		   글목록으로 리다이렉트
     */
    @GetMapping("delete")
    public String delete(
    		@RequestParam("boardNum") int boardNum,
    		@AuthenticationPrincipal AuthenticatedUser user
    		) {
    	log.debug("삭제할 판매글 : {}, 사용자 아이디 : {}", boardNum, user.getUsername());
    	boardService.delete(boardNum, user.getUsername());
    	return "redirect:list";
    }
}
