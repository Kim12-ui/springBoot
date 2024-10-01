package net.datasa.test.domain.entity;

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
@Table(name = "market_member")
public class MemberEntity {

   // 사용자 아이디
	@Id
	@Column(name="member_id", nullable = false)
	String memberId;
	
	// 비밀번호
	@Column(name = "member_pw", nullable = false)
	String memberPw;
	
	// 사용자 이름
	@Column(name = "member_name", nullable = false)
	String memberName;
	
	// 전화번호
	@Column(name = "phone", nullable = false)
	String phone;
}
