package com.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("local")
public class LocalController {

	@GetMapping("save")
	public String save() {
		return "local/save";
	}
	
	@GetMapping("read")
	public String read() {
		return "local/read";
	}
	
	@GetMapping("delete")
	public String delete() {
		return "local/delete";
	}
}