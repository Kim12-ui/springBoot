package com.dsa.web5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dsa.web5.dto.BoardDTO;
import com.dsa.web5.security.AuthenticatedUser;
import com.dsa.web5.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService boardService;
	
	// application.properties 파일의 게시판 관련 설정값
	// import org.springframework.beans.factory.annotation.Value; 실행
	@Value("${board.uploadPath}")
	String uploadPath;
	
	/**
	 * 글쓰기 폼으로 이동
	 * @return writeForm.html
	 */
	@GetMapping("write")
	public String write() {
		return "board/writeForm";
	}
	
	/**
	 * 글 저장
	 * @param boardDTO 작성한 글 정보
	 * @param user 로그인한 사용자 정보
	 * @param MultipartFile 첨부파일
	 * @return listAll.html
	 */
	@PostMapping("write")
	public String write(
			@ModelAttribute BoardDTO boardDTO,
			@RequestParam(name = "upload", required = false) MultipartFile upload,
			@AuthenticationPrincipal AuthenticatedUser user
			) {
		
		boardDTO.setMemberId(user.getUsername());
		log.debug("저장할 첨부파일 정보: {}", upload.getOriginalFilename());
		log.debug("저장할 글 정보: {}", boardDTO);
		
		try {
			boardService.write(boardDTO, uploadPath, upload);
			return "redirect:/board/listAll";
		} catch(Exception e) {
			e.printStackTrace();
			return "board/writeForm";
		}
	}
	
	/**
	 * 게시판 전체 목록 조회. 페이징X
	 * @param boardList 게시판
	 * @param model
	 * @return
	 */
	@GetMapping("listAll")
	public String listAll(Model model) {
		List<BoardDTO> boardList = boardService.selectAllList();
		model.addAttribute("boardList",boardList);
		return "board/listAll";
	}
	
	/**
	 * 게시글 상세보기
	 * @param Model
	 * @param boardNum 조회할 글 번호
	 * @return read.html
	 */
	@GetMapping("read")
	public String read(
			Model model,
			@RequestParam(name = "boardNum", defaultValue = "0") int boardNum
			) {
		log.debug("boardNum: {}", boardNum);
		BoardDTO boardDTO = boardService.getBoard(boardNum);
		model.addAttribute("board",boardDTO);
		
		return "board/read";
	}
}
