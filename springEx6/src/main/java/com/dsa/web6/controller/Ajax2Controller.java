package com.dsa.web6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsa.web6.dto.PersonDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class Ajax2Controller {
	
	@GetMapping("ajax2")
	public String ajax2() {
		return "ajax/ajax2";
	}
	
	/**
	 * 파라미터를 각각의 변수로 전달
	 */
	@ResponseBody
	@PostMapping("insert1")
	public void insert1(
			@RequestParam("name") String name,
			@RequestParam("age") String age,
			@RequestParam("phone") String phone
			) {
		log.debug("insert1 : name={}, age={}, phone={}", name, age, phone);
	}
	
	/**
	 * 파라미터를 DTO 객체로 전달받기 1
	 */
	@ResponseBody
	@PostMapping("insert2")
	public void insert2(@ModelAttribute PersonDTO person) {
		log.debug("insert2() : {}",person);
	}
	
	/**
	 * 파라미터를 DTO 객체로 전달받기 2
	 */
	@ResponseBody
	@PostMapping("insert3")
	public void insert3(@ModelAttribute PersonDTO person) {
		log.debug("insert3() : {}",person);
	}
	
	/**
	 * 객체 리턴
	 */
	@ResponseBody
	@GetMapping("getObject")
	public PersonDTO getObject() {
		PersonDTO person = new PersonDTO("홍길동",20,"010-1111-2222");
		return person;
	}
}
