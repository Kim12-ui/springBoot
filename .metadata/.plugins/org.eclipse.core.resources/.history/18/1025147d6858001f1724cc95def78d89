package com.dsa.web5.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.dsa.web5.security.AuthenticatedUser;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	@GetMapping({"","/"})
	public String home() {
		return "home";
	}
	
	@GetMapping("thymeleaf")
	public String thymeleaf(@AuthenticationPrincipal AuthenticatedUser user) {
		if(user != null) {
			log.debug("Authentication 객체 정보 출력");
			log.debug(user.getUsername());
			log.debug(user.getPassword());
			log.debug(" *"+user.getAuthorities());
			log.debug(" *"+user.isAccountNonExpired());
			log.debug(" *"+user.isAccountNonLocked());
			log.debug(" *"+user.isCredentialsNonExpired());
			log.debug(" *"+user.isEnabled());	
		}
		
		return "thymeleaf";
	}
}
