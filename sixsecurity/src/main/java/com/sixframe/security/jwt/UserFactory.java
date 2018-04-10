package com.sixframe.security.jwt;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.sixframe.security.entity.JwtUser;

public interface UserFactory<T,U> {
	public JwtUser create(T user);
	
	List<GrantedAuthority> mapToGrantedAuthorities(List<U> roles);
}
