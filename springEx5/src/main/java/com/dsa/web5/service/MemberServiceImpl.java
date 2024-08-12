package com.dsa.web5.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsa.web5.dto.MemberDTO;
import com.dsa.web5.entity.MemberEntity;
import com.dsa.web5.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository mr;

	// 암호화 (WebSecurityConfig에 있는 객체)
	private final BCryptPasswordEncoder passwordEncoder;
	
	/**
	 * 가입시 아이디 중복 확인 처리
	 * @param searchId
	 * @return 해당 아이디로 가입 가능 여부 true / false
	 */
	@Override
	public boolean idCheck(String searchId) {
		return !mr.existsById(searchId);
	}

	/**
	 * 회원가입 처리
	 * @param MemberDTO 가입 데이터
	 */
	@Override
	public void join(MemberDTO member) {
		MemberEntity m = MemberEntity.builder()
				.memberId(member.getMemberId())
				// 444 -> $2a$10$GAhGwIkeZAFF.uBJ2X4XK.ZNJ.r39ewPNJlHlsOoN1xMEFCg4RVrO
				// (단방향)해싱함수로 암호화
				.memberPassword(
					passwordEncoder.encode(member.getMemberPassword())
						)
				.memberName(member.getMemberName())
				.phone(member.getPhone())
				.email(member.getEmail())
				.address(member.getAddress())
				.build();
		mr.save(m);
	}
		
}
