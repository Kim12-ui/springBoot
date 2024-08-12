package com.dsa.web3.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dsa.web3.dto.MemberDTO;
import com.dsa.web3.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService ms;
	
	/*
	 * 회원가입 폼으로 이동
	 * @return joinForm.html로 이동
	 * */
	@GetMapping("join")
	public String join() {
		return "member/joinForm";
	}
	
	// 내가 적은 답안 (회원가입)은 주석처리
//	@PostMapping("join")
//	public String joinMember(
//			@RequestParam(name="id") String id,
//			@RequestParam(name="pw") String pw,
//			@RequestParam(name="name") String name,
//			@RequestParam(name="phone") String phone,
//			@RequestParam(name="address") String address			
//			
//			) {
//		ms.joinMember(id, pw, name, phone, address);
//		return "redirect:/";
//	}
	
	/*
	 * 회원가입 처리
	 * @param member
	 * @return home.html
	 * */
	@PostMapping("join")
	public String join(@ModelAttribute MemberDTO member) {
		// @ModelAttribute 메서드 파라미터에 사용하며, 요청 파라미터를 객체에 바인딩
		log.debug("[MemberController-join] member: {}",member);
		ms.joinData(member);
		return "redirect:/";
	}
	
	/*
	 * 로그인 폼으로 이동
	 * @return loginForm.html
	 * */
	@GetMapping("login")
	public String login() {
		return "member/loginForm";
	}
	
	/*
	 * 로그인 처리
	 * @param id
	 * @param pw
	 * @param HttpSession 객체
	 * @return loginForm.html
	 * */
	@PostMapping("login")
	public String login(
				HttpSession session,
				@RequestParam(name="id") String id,
				@RequestParam(name="pw") String pw
			) {
		// id값을 파라미터로 DB에 회원정보가 존재하는지를 판별
		MemberDTO member = ms.selectData(id);
		
		if(member != null) {
			// 만약 입력정보가 DB에 존재한다면 패스워드가 일치하는지 판별
			if (member.getPw().equals(pw)) {
				// 일치한 정보일 경우 session 객체에 저장
				session.setAttribute("loginId", member.getId());
				String currentId = (String) session.getAttribute("loginId");
				log.debug("현재 로그인한 ID: {}",currentId);
				return "redirect:/";
			}
			else {
				// 비밀번호가 일치하지 않는경우
				log.debug("로그인 비밀번호가 일치하지 않습니다.");
				return "member/loginForm";
			}
		}
		else {
			log.debug("로그인 실패! DB에 저장된 회원정보가 없음");
			return "member/loginForm";
		}
	}
	
	/*
	 * 로그아웃 처리
	 * @param HttpSession
	 * @return home.html 
	 * */
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginId");
		String sessionId = (String) session.getAttribute("loginId");
		log.debug("로그아웃, 현재 세션정보: {}",sessionId);
		return "redirect:/";
	}
	
	/*
	 * 회원수정 폼으로 이동
	 * @param HttpSession
	 * @return updateForm.html
	 * */
	@GetMapping("update")
	public String update(HttpSession session,Model model) {
		// 로그인한 유저의 id를 세션 객체로부터 불러오기
		String sessionId = (String) session.getAttribute("loginId");
		// id값에 일치하는 DB 회원정보를 자바객체로 가져오기
		MemberDTO member = ms.selectData(sessionId);
		// HTML에 출력하기 위해서 model 객체에 해당 객체 저장
		model.addAttribute("member",member);
		
		return "member/updateForm";
	}
	
	/*
	 * 회원정보 수정 처리
	 * @param MemberDTO
	 * @return home.html
	 * */
	@PostMapping("update")
	public String updateMember(@ModelAttribute MemberDTO member) {
		ms.updateData(member);
		return "redirect:/";
	}
	
	/*
	 * 회원정보조회 폼으로 이동
	 * @return selectForm.html
	 * */
	@GetMapping("select")
	public String select() {
		return "member/selectForm";
	}
	
	/**
	 * 검색폼에서 입력한 아이디를 전달받아 회원정보 조회 처리
	 * @param searchId
	 * @param model
	 * @return member/select
	 */
	@PostMapping("select")
	public String select(
			@RequestParam(name="searchId") String searchId,
			Model model
			) {
		log.debug("검색아이디: {}",searchId);
		MemberDTO member = ms.selectData(searchId);
		
		model.addAttribute("searchId",searchId);
		model.addAttribute("member",member);
		return "member/select";
	}
	
	/**
	 * url로부터 들어온 요청을 처리하는 메서드
	 * http://localhost:9992/web3/member/info/abc 형식으로 요청
	 * @param id
	 * @param model
	 * @return select.html
	 */
	@GetMapping("info"+"/{id}")
	public String info(
			// 요청 URL의 일부를 메서드 파라미터로 매핑
			@PathVariable(name="id") String id,
			Model model
			) {
		MemberDTO member = ms.selectData(id);
		
		model.addAttribute("searchID",id);
		model.addAttribute("member",member);
		return "member/select";
		// 웹페이지에 http://localhost:9992/web3/member/info/"ID값을 입력"
	}
	
	/**
	 * 회원정보 전체조회
	 * @param Model
	 * @return list.html
	 */
	// 회원목록
	@GetMapping("list")
	public String list(Model model) {
		List<MemberDTO> memberList = ms.selectAllData();
		model.addAttribute("memberList",memberList);
		return "member/list";
	}
	
	/**
	 * 회원정보 삭제 처리
	 * @param id
	 * @return list.html
	 */
	@GetMapping("delete")
	public String delete(
			@RequestParam(name="id") String id
			) {
		log.debug("삭제처리하기 위해 받아온 id: {}",id);
		
		boolean result = ms.deleteData(id);
		
		log.debug("삭제처리: {}", (result ? "성공":"실패"));
		
		return "redirect:/member/list";
	}
}