package com.dsa.web6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsa.web6.service.AjaxService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ExController {

	private final AjaxService as;
	
	/**
	 * 아이디 중복 확인 페이지로 이동
	 * @return idDuplicate.html
	 */
	@GetMapping("idDuplicate")
	public String idDuplicate() {
		return "example/idDuplicate";
	}
	
	/**
	 * 아이디 존재 여부 확인
	 * @param id 조회할 아이디
	 * @return result true/false 아이디가 존재하면 true, 존재하지 않으면 false
	 */
	@ResponseBody
	@PostMapping("idDuplicate")
	public Boolean idCheck(@RequestParam("id") String id) {
		log.debug("조회할 아이디 : {}",id);
		
		boolean result = as.idDuplicate(id);
		log.debug("결과: {}",result);
		
		return result;
	}
}
