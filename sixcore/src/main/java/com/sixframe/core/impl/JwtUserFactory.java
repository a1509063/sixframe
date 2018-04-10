package com.sixframe.core.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.sixframe.dao.entity.Authority;
import com.sixframe.dao.entity.Roles;
import com.sixframe.dao.entity.User;
import com.sixframe.security.entity.JwtUser;
import com.sixframe.security.jwt.UserFactory;

public class JwtUserFactory implements UserFactory<User,Roles> {
	private JwtUserFactory() {
	}
	
	public JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUserName(),
                user.getPassWord(),
                user.getLastPasswordResetDate(),
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                mapToGrantedAuthorities(user.getRoles()==null?new ArrayList<Roles>():user.getRoles())
        );
    }
	
	public final List<GrantedAuthority> mapToGrantedAuthorities(List<Roles> roles) {
		List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
		list.add(new SimpleGrantedAuthority("ROLE_USER"));
		list.add(new SimpleGrantedAuthority("ROLE_TOKEN_CHECK"));
		for (int i = 0; i < roles.size(); i++) {
			List<Authority> authorities = roles.get(i).getAuthorities();
			for (Authority authority : authorities) {
				GrantedAuthority au = new SimpleGrantedAuthority(authority.getAuthorityName());
				if(!list.contains(au))
					list.add(au);
			}
		}
		return list;
	}

}
