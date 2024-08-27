package com.dsa.web6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsa.web6.service.AjaxService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ExController {

	@Autowired
	AjaxService as;
	
	@GetMapping("idDuplicate")
	public String idDuplicate() {
		return "example/idDuplicate";
	}
	
	@ResponseBody
	@PostMapping("idCheck")
	public boolean idCheck(@RequestParam("idValue") String idValue) {
		log.debug(idValue);
		
		boolean result = as.idCheck(idValue);
		log.debug("결과: {}",result);
		
		return result;
	}
}
