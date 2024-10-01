package com.dsa.web6.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	/**
     * 메인화면으로 이동
     */
    @GetMapping({"", "/"})
    public String Home() {
        return "Home";
    }
    
    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        boolean isLoggedIn = principal != null; // principal이 null이 아니면 로그인 상태
        model.addAttribute("isLoggedIn", isLoggedIn);
        return "home"; // 뷰 이름
    }

}
