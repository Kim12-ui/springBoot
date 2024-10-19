package com.dsa.tabidabi.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dsa.tabidabi.domain.dto.MemberDTO;
import com.dsa.tabidabi.domain.entity.MemberEntity;
import com.dsa.tabidabi.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	
	// 암호화 (WebSecurityConfig에 있는 객체)
	private final BCryptPasswordEncoder passwordEncoder;

	/**
	 * 아이디 중복 확인
	 * @return 아이디 중복시 true, 아이디 중복X시 false
	 */
	@Override
	public boolean idDuplicate(String id) {
		return memberRepository.existsById(id);
	}

	/**
	 * 회원가입 처리
	 * @param MemberDTO 가입 데이터
	 */
	@Override
	public void join(MemberDTO member) {
		MemberEntity entity = MemberEntity.builder()
							  .memberId(member.getMemberId())
							  .password(passwordEncoder.encode(member.getPassword()))
							  .nickname(member.getNickname())
							  .email(member.getEmail())
							  .build();
		
		memberRepository.save(entity);
	}

	/**
     * 회원 ID로 회원 정보 조회
     * @param memberId 조회할 회원 ID
     * @return 회원 DTO
     */
	@Override
    public MemberDTO findById(String memberId) {
        // 회원 정보를 조회하고, 없을 경우 예외 발생
        MemberEntity entity = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        
        // 조회된 엔티티를 DTO로 변환하여 반환
        return MemberDTO.builder()
                .memberId(entity.getMemberId()) // 회원 ID
                .email(entity.getEmail()) // 이메일
                .nickname(entity.getNickname()) // 회원 닉네임
                .build();
    }

    /**
     * 회원 정보 수정
     * @param dto 수정할 회원 정보
     */
	@Override
	public void update(MemberDTO dto) {
	    // 회원 ID로 기존 회원 정보 조회
	    MemberEntity entity = memberRepository.findById(dto.getMemberId())
	            .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
	    
	    // 수정할 필드 설정
	    entity.setNickname(dto.getNickname());
	    entity.setEmail(dto.getEmail());
	    
	    // 프로필 이미지 업데이트
	    if (dto.getProfileImage() != null) {
	        entity.setProfileImage(dto.getProfileImage()); // 프로필 이미지 업데이트
	    }

	    // 비밀번호 변경이 필요한 경우 추가 로직 구현
	    if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
	        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
	    }

	    memberRepository.save(entity);
	}

	
}