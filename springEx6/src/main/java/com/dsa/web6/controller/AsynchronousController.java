package com.dsa.web6.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dsa.web6.dto.PersonDTO;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AsynchronousController {

	@GetMapping("fetch")
	public String fetch() {
		return "ajax/fetch";
	}
	
	@ResponseBody
	@GetMapping("fetch1")
	public String fetch1(@RequestParam("text") String text) {
		log.debug("* fetch1_param: {}", text);
		return "server";
	}
	
	@ResponseBody
	@PostMapping("fetch2")
	public PersonDTO fetch2(
			/*
			 * @RequestBody
			 * - HTTP 요청 본문 데이터를 Java 객체로 변환
			 */
			@RequestBody(required=false) PersonDTO person
			) {
		log.debug("* fetch2_param: {}",person);
		person.setName("홍길동");
		person.setAge(30);
		person.setPhone("010-9999-9999");
		return person;
	}
	
	@ResponseBody
	@PostMapping("fetch3")
	public ArrayList<PersonDTO> fetch3(
			@RequestBody ArrayList<PersonDTO> personList
			) {
		for (PersonDTO p : personList) {
			log.debug("* fetch3_param: {}", p);
		}
		return personList;
	}
}
