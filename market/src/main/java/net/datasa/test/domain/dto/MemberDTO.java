package net.datasa.test.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

	// 사용자 아이디
	String memberId;
	
	// 비밀번호
	String memberPw;
	
	// 사용자 이름
	String memberName;
	
	// 전화번호
	String phone;
	
	
}
