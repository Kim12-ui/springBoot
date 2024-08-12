package com.dsa.web99.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
	
	// href="path111", return=image111.html문서 불러오기
	// <a href="path111">이미지 html로</a> 클릭시
	// http://localhost:9999/web99/path111 (image111.html) 웹페이지로 이동
	@GetMapping("path111")
	public String image() {
		return "image111";
	}
}