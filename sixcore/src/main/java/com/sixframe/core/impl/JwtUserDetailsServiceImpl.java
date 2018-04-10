package com.sixframe.core.impl;
//package com.sixframe.security.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import com.demo.app.dao.entity.User;
//import com.demo.app.dao.main.mapper.UserMapper;
//
//@Component
//public class JwtUserDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//    private UserMapper userMapper;
//	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userMapper.findByUsername(username);
//		User userDetails = userMapper.findUserInfoById(user.getId());
//        if (userDetails == null) {
//            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
//        } else {
//            return JwtUserFactory.create(userDetails);
//        }
//	}
//}
