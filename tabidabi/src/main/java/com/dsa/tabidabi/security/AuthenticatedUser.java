package com.dsa.tabidabi.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 인증 정보 객체
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticatedUser implements UserDetails {
	
	//인증 관련 정보
	String id;
	String password;
	String nickname;
	String email;

	//권한명 리턴
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
	
	//사용자 아이디 리턴
	@Override
	public String getUsername() {
		return this.id;
	}
	
	//비밀번호 리턴
	@Override
	public String getPassword() {
		return this.password;
	}
	
	//비밀번호 리턴
	public String getNickname() {
		return this.nickname;
	}
	
	//이메일 리턴
	public String getEmail() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
    public boolean isEnabled() {
        return true;
    }
}
