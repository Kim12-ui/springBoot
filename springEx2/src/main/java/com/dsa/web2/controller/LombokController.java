package com.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dsa.web2.vo.Person;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("lombok")
public class LombokController {
	
	/*
	  lombok
	  자바 어플리케이션 개발에서 @Annotation을 활용하여 코드를 줄여주고
	  가독성을 높여주는 라이브러리
	      @Getter, @Setter, @Tostring, @EqualsAndHashCode
	      @NoArgsConstructor: 매개변수가 없는 기본 생성자를 생성
	      @AllArgsConstuctor: 모든 필드를 매개변수로 받는 생성자를 생성
	      @RequiredArgsConstructor: final 또는 @NonNull 필드를
	          매개변수로 받는 생성자를 생성
	      @Data
	          @Getter, @Setter, @Tostring, @EqualsAndHashCode를 한번에 생성
	      @Builder
	*/
	
	@GetMapping("lombok1")
	public String lombok1() {
		// 인스턴스 생성 후 setter를 이용한 초기화
		Person p = new Person();
		p.setName("홍길동");
		p.setAge(99);
		p.setPhone("01011112222");
		
		// builder를 이용한 초기화
		Person p2= Person.builder()
				.name("이순신")
				.age(80)
				.phone("01022223333")
				.build();
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
		// 요청을 다시 보냄
		return "redirect:/";
	}
}