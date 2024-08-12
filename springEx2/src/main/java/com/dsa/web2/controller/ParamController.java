package com.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsa.web2.vo.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("param")
public class ParamController {
	
	@GetMapping("view1")
	public String view1() {
		log.debug("param/view1 요청처리");
		return "param/view1";
	}
	
	@GetMapping("param1")
	public String param1(
			@RequestParam(name="name") String name,
			@RequestParam(name="age") int age,
			@RequestParam(name="phone") String phone
			) {
		log.debug("param1 log: name={}, age={}, phone={}"
				,name,age,phone);
		return "redirect:/";
	}
	
	@GetMapping("view2")
	public String view2() {
		return "param/view2";
	}
	
	@PostMapping("param2")
	public String param2(
				@RequestParam(name="name") String name,
				@RequestParam(name="age") int age,
				@RequestParam(name="phone") String phone
			) {
		log.debug("param2 log : name={}, age={}, phone={}"
				,name,age,phone);
		return "redirect:/";
	}
	
	@GetMapping("view3")
	public String view3() {
		return "param/view3";
	}
	
	@PostMapping("param3")
	public String param3(Person p) {
		log.debug("param3 log : person={}",p);
		return "redirect:/";
	}
	
	@GetMapping("param4")
	public String param4(Person p) {
		log.debug("param4 log: person={}",p);
		return "redirect:/";
	}
	
	@GetMapping("param5")
	public String param5(
				@RequestParam(name="name", defaultValue="아무개") String name,
				@RequestParam(name="age", defaultValue="0") int age,
				@RequestParam(name="phone", defaultValue="01012345678") String phone
			) {
		log.debug("param5 log : name={}, age={}, phone={}",
				name,age,phone);
		return "redirect:/";
	}
	
	// ctrl+shift+o 실행 후 org.springframework.ui.~~ 선택 후 적용
	@GetMapping("model")
	public String model(Model model) {
		String str = "문자열";
		int num=100;
		Person p = new Person("홍길동",33,"01022223333");
		
		// model 객체 활용
		// key - value
		// controller -> html로 보낼때까지만 유효한 데이터
		model.addAttribute("str",str);
		model.addAttribute("num",num);
		model.addAttribute("person",p);
		
		return "param/model";
	}
}
