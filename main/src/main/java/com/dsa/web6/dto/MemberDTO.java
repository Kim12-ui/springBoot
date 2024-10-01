package com.dsa.web6.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원정보 DTO
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    //사용자 아이디
    String memberId;

    //비밀번호
    String memberPw;

    //사용자 이름
    String memberName;

    //닉네임
    String memberNickName;

    //프로필 사진 경로
    private String profileImage; // 프로필 이미지 경로 필드 추가
}
