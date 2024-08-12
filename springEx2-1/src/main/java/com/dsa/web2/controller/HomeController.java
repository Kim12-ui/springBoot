package com.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {

	@GetMapping({"","/"})
	public String home() {
		int a=100;
		log.debug("home() 실행 {}",a);
		return "home";
	}
}