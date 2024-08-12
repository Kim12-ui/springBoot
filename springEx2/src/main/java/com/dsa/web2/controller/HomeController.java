package com.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

// 사용자로부터 요청을 받아 메서드로 매핑시켜주는 역할
@Slf4j
@Controller
public class HomeController {
	
	// 상기 요청과 일치하는 요청일 경우 아래 메서드를 실행
	@GetMapping({"","/"})
	public String home() {
		int a=100;
		log.debug("home() 실행 {}",a);
		// templates 패키지에서부터 리턴하는 문자열로 만들어진
		// html 문서를 찾아서 리턴
		return "home";
	}
	
	@GetMapping("html")
	public String html() {
		return "html";
	}
	
	@GetMapping("css")
	public String css() {
		return "css";
	}
	
	@GetMapping("js")
	public String js() {
		return "js";
	}
}
