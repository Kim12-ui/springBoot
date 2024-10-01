package com.dsa.web6.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dsa.web6.dto.MemberDTO;
import com.dsa.web6.entity.MemberEntity;
import com.dsa.web6.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * 회원정보 서비스
 */
@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    //WebSecurityConfig에서 생성한 암호화 인코더
    private final BCryptPasswordEncoder passwordEncoder;

    //회원 정보 관련 리포지토리
    private final MemberRepository memberRepository;

    /**
     * 회원 가입 처리
     * @param dto 회원 정보
     */
    public void join(MemberDTO dto) {
        //전달된 회원정보를 테이블에 저장한다.
        MemberEntity entity = MemberEntity.builder()
                .memberId(dto.getMemberId())
                .memberPw(passwordEncoder.encode(dto.getMemberPw()))
                .memberName(dto.getMemberName())
                .memberNickName(dto.getMemberNickName())
                .build();

        memberRepository.save(entity);
    }
    
    public MemberDTO findById(String memberId) {
        MemberEntity entity = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        
        return MemberDTO.builder()
                .memberId(entity.getMemberId())
                .memberName(entity.getMemberName())
                .memberNickName(entity.getMemberNickName())
                .build();
    }

    // 회원 정보 수정 메서드 추가
    public void update(MemberDTO dto) {
        MemberEntity entity = memberRepository.findById(dto.getMemberId()).orElseThrow(() -> new RuntimeException("회원 정보를 찾을 수 없습니다."));
        
        entity.setMemberName(dto.getMemberName());
        entity.setMemberNickName(dto.getMemberNickName());
        
        // 프로필 이미지 업데이트
        if (dto.getProfileImage() != null) {
            entity.setProfileImage(dto.getProfileImage());
        }

        // 비밀번호 변경이 필요한 경우 추가 로직 구현
        if (dto.getMemberPw() != null && !dto.getMemberPw().isEmpty()) {
            entity.setMemberPw(passwordEncoder.encode(dto.getMemberPw()));
        }

        memberRepository.save(entity); // 변경된 엔티티 저장
    }


}
