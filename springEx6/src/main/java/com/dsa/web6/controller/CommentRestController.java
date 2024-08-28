package com.dsa.web6.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dsa.web6.dto.CommentDTO;
import com.dsa.web6.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("comment")
// @RestController = @Controller + @ResponseBody
// 클래스 내의 모든 메서드에 @ResponseBody 적용
@RestController
public class CommentRestController {

	private final CommentService service;
	
	/**
	 * 댓글 목록 조회
	 * @return 댯글 목록
	 */
	@GetMapping("list")
	public ArrayList<CommentDTO> list() {
		ArrayList<CommentDTO> list = service.getList();
		return list;
	}
	
	/**
	 * 댓글 저장
	 * @param commentDTO 저장할 댓글 정보
	 */
	@PostMapping("save")
	public void save(@ModelAttribute CommentDTO commentDTO) {
		log.debug("저장할 댓글 정보 : {}",commentDTO);
		service.save(commentDTO);
	}
	
	/**
	 * 댓글 삭제
	 * @param num 삭제할 댓글 번호
	 */
	@PostMapping("delete")
	public void delete(@RequestParam("num") Integer num) {
		service.delete(num);
	}
	
	/**
	 * 댓글 수정
	 * @param CommentDTO 수정할 댓글 정보
	 */
	@PostMapping("update")
	public void update(@ModelAttribute CommentDTO dto) {
		log.debug("수정할 댓글 정보 : {}",dto);
		service.update(dto);
	}
}
