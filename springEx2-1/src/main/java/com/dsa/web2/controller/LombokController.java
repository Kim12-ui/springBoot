package com.dsa.web2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsa.web2.vo.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("lombok")
@Controller
public class LombokController {
	@GetMapping("lombok1")
	public  String lombok1() {
		Person p = new Person();
		p.setName("홍길동");
		p.setAge(99);
		p.setPhone("01011112222");
		
		Person p2=Person.builder().name("이순신").age(80).phone("01022223333").build();
		log.debug("Person객체 {}",p);
		log.debug("Person객체 {}",p2);
		return "lombok/lombok1";
	}
	
	@GetMapping("lombok2")
	public String lombok2() {
		log.error("error level");
		log.warn("warn level");
		log.info("info level");
		log.debug("debug level");
		log.trace("trace level");
		
		return "redirect:/";
	}
}