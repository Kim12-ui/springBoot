package com.dsa.web4.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsa.web4.dto.GuestBookDTO;
import com.dsa.web4.service.GuestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("guest")
@RequiredArgsConstructor
public class GuestController {

	private final GuestService gs;
	
	/**
	 * 입력 폼으로 이동
	 * @return writeForm.html
	 */
	@GetMapping("write")
	public String write() {
		return "writeForm";
	}
	
	/**
	 * 작성한 글 전달받아 저장
	 * @param guest
	 * @return guestList.html
	 */
	@PostMapping("write")
	public String write(
			@ModelAttribute GuestBookDTO guest
			) {
		log.debug("[GuestController-write] guest: {}",guest);
		gs.writeData(guest);
		return "redirect:/guest/guestList";
	}
	
	/**
	 * 방명록 접속
	 * @param model
	 * @return guestList.html
	 */
	@GetMapping("guestList")
	public String list(Model model) {
		List<GuestBookDTO> guestList = gs.selectAllData();
		model.addAttribute("guestList",guestList);
		return "guestList";
	}
	
	/**
	 * 방명록 삭제 처리
	 * @param num
	 * @return guestList.html
	 */
	@GetMapping("delete")
	public String delete (
			@RequestParam(name="num") Integer num,
			@RequestParam(name="password") String password
			) {
		log.debug("삭제처리하기 위해 받아온 num: {}, password: {}",num, password);
		
		GuestBookDTO guestbookDTO = gs.selectData(num);
		
		boolean result = false;
		if(password.equals(guestbookDTO.getPassword())) {
			result = gs.deleteData(guestbookDTO.getNum());
		}
		log.debug("삭제처리: {}", (result?"성공":"실패"));
		return "redirect:/guest/guestList";
	}
}