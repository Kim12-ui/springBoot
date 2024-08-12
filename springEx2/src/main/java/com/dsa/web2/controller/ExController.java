package com.dsa.web2.controller;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("ex")
public class ExController {

	@GetMapping("info")
	public String info() {
		return "ex/info";
	}
	
	// 예제1. 주민번호 예제
	@GetMapping("output")
	public String output(
			@RequestParam(name="name") String name,
			@RequestParam(name="ssn") String ssn,
			Model model
			) {
		/*
		  文.
		  info.html 에서 입력받은 데이터를 파라미터로 받아오기
		  주민번호의 각 자기별 정보를 통해 나이, 생년월일, 성별을 추출
		  model을 통해 output.html에 이름, 나이, 생년월일, 성별을 전송
		*/
		log.debug("output_param: name={}, ssn={}",name,ssn);
		char gender=ssn.charAt(7);
		String genderResult=(gender=='1'||gender=='3')?"남자":"여자";
		int year=Integer.parseInt(ssn.substring(0,2));
		int month=Integer.parseInt(ssn.substring(2,4));
		int day=Integer.parseInt(ssn.substring(4,6));
		
		Calendar c = Calendar.getInstance();
		int y=c.get(Calendar.YEAR);
		
		int age;
		if(gender=='1'||gender=='2') {
			age=y-year-1900;
		}
		else {
			age=y-year-2000;
		}
		String birth=String.format("%d년 %d월 %d일",year,month,day);
		model.addAttribute("name",name);
		model.addAttribute("age",age);
		model.addAttribute("birth",birth);
		model.addAttribute("gender",genderResult);
		return "ex/output";
	}
	
	// 예제2. Cookie 객체를 이용한 방문횟수 count 예제
	@GetMapping("count")
	public String count(
			@CookieValue(name="count", defaultValue="0") int count,
			HttpServletResponse response,
			Model model
			) {
		count++;
		model.addAttribute("count",count);
		// 1. 쿠키객체 생성
		// 2. 쿠키객체 읽기
		// 3. 카운트 증가
		Cookie c = new Cookie("count",Integer.toString(count));
		c.setMaxAge(60*60*24);
		response.addCookie(c);
		return "ex/count";
	}
}