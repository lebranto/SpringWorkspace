package com.kh.spring.security.model.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kh.spring.member.model.vo.Member;

public class MemberExt extends Member implements UserDetails{

	private List<SimpleGrantedAuthority> authorities;
	
	//SimpleGrantedAuthority
	// - 문자열 셩태의 권한
	// - "ROLE_USER",ROLE_ADMIN  등.
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		// 사용자가 가진 권한 목록을 반환하는 메서드
		
		return this.authorities;
	}
	
	
	//getPassword / getUsername
	// - 시큐리티에서 비밀번호/ 아이디를 가져올 때 사용하는 메서드
	// - id역할의 필드가 username이 아니거나, 비밀번호 역할의 필드가 password가
	//  아닌경우 오버라이딩이 필요

	@Override
	public String getPassword() {
		return getUserPwd();
	}

	@Override
	public String getUsername() {
		return getUserId();
	}
	
	
	
	// 계정의 만료상태, 잠금상태, 사용가능여부 등을 반환하는 메서드
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
		
	// 특정 id를 정지시킬 떄 사용
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
		// TODO Auto-generated method stub
		return true;
	}
	
	

}