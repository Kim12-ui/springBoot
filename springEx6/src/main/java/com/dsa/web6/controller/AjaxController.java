package com.dsa.web6.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AjaxController {

	@GetMapping("ajax1")
	public String ajax1() {
		return "ajax/ajax1";
	}
	
	/**
	 * Ajax 요청에 대한 응답
	 * @ResponseBody : 일반적인 Spring MVC 요청 처리에서는 데이터를 반환하지 않고,
	 * 				   JSP, Thymeleaf와 같은 뷰를 반환
	 * 				   but, 해당 어노테이션이 붙은 메서드는 컨트롤러 메서드가 반환하는 데이터를
	 * 				   HTTP 응답 본문(Body)에 직접 쓰는데 사용
	 * 				   즉, 뷰를 반환하는게 아니라, 데이터 자체를 클라이언트에게 반환
	 * 				   > 이 과정은 뷰 렌더링을 거치지 않으므로, JSON, XML, 문자열 등의
	 * 					 형식으로 직접 클라이언트에게 전달
	 */
	@ResponseBody
	@GetMapping("ajaxTest1")
	public void ajaxTest1() {
		log.debug("AjaxController의 ajaxTest1() 실행");
	}
	
	@ResponseBody
	@PostMapping("ajaxTest2")
	public void ajaxTest2(@RequestParam("str") String str) {
		log.debug("ajaxTest2() 실행: {}",str);
	}
	
	@ResponseBody
	@GetMapping("ajaxTest3")
	public String ajaxTest3() {
		String msg = "서버에서 보낸 메시지";
		log.debug("ajaxTest3() 실행: {}",msg);
		return msg;
	}
	
	@ResponseBody
	@PostMapping("ajaxTest4")
	public int ajaxTest4(
			@RequestParam("num1") int a,
			@RequestParam("num2") int b
			) {
		return a + b;
	}
	
	@ResponseBody
	@PostMapping("ajaxTest5")
	public ResponseEntity<?> ajaxTest5(
			@RequestParam("num4") String a,
			@RequestParam("num5") String b
			) {
		/**
		 * ResponseEntity<?>와 @ResponseBody 차이점
		 * - @ResponseBody는 컨트롤러 메서드의 반환값을 HTTP 응답 본문으로 직렬화하여 반환.
		 *   싱테코드 및 헤더를 변경할 수 없음.
		 * - ResponseEntity<?>는 응답 본문뿐만 아니라 상태 코드 및 헤더도 함께 설정 가능
		 */
		try {
			int n1 = Integer.parseInt(a);
			int n2 = Integer.parseInt(b);
			int n3 = n1 / n2;
			return ResponseEntity.ok(n3);
		} catch (NumberFormatException e) {
			// HTTP 응답 상태코드 400과 에러 메시지를 본문으로 리턴
			return ResponseEntity.badRequest().body("정수가 아닙니다.");
		} catch (ArithmeticException e) {
			return ResponseEntity.badRequest().body("0으로 나눌 수 없습니다.");
		}
	}
	
	@PostMapping("ajaxTest6")
	public ResponseEntity<?> ajaxTest6(
			@RequestParam("num7") String a,
			@RequestParam("num8") String b,
			@RequestParam("op") String op
			) {
		try {
			int n1 = Integer.parseInt(a);
			int n2 = Integer.parseInt(b);
			int n3 = 0;
			
			switch(op) {
			case "+" -> n3 = n1+n2;
			case "-" -> n3 = n1-n2;
			case "*" -> n3 = n1*n2;
			case "/" -> n3 = n1/n2;
			default -> {
				return ResponseEntity.badRequest().body("연산자를 확인해 주세요");
				}
			}
			
			return ResponseEntity.ok(n3);
		} catch(NumberFormatException e) {
			return ResponseEntity.badRequest().body("정수가 아닙니다.");
		} catch(ArithmeticException e) {
			return ResponseEntity.badRequest().body("0으로 나눌 수 없습니다.");
		}
	}
	
}
