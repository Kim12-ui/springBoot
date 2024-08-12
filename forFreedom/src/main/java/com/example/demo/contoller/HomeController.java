package com.example.demo.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping({"","/"})
	public String home() {
		return "home";
	}
	
	@GetMapping("doraemon")
	public String doraemon() {
		return "doraemon";
	}
	
	@GetMapping("checkPoint")
	public String checkPoint() {
		return "checkPoint";
	}
	
	@GetMapping("image")
	public String image() {
		return "image";
	}
	
	@GetMapping("sub/image2")
	public String image2() {
		return "image2";
	}
	
	@GetMapping("js")
	public String js() {
		return "javascript/js";
	}
}
