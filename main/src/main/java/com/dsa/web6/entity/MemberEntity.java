package com.dsa.web6.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원정보 Entity
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "main_member")
public class MemberEntity {

    //사용자 아이디
    @Id
    @Column(name = "member_id", nullable = false)
    String memberId;

    //비밀번호
    @Column(name = "member_pw", nullable = false)
    String memberPw;

    //사용자 이름
    @Column(name = "member_name", nullable = false)
    String memberName;

    //닉네임
    @Column(name = "member_nickname", nullable = false)
    String memberNickName;

    //사진
    @Column(name = "profile_image") // 프로필 이미지 컬럼 추가
    private String profileImage; // 프로필 이미지 경로를 저장할 필드 추가
}
