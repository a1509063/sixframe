package com.sixframe.dao.main.mapper.sqlprovider;

import java.util.List;

import com.sixframe.dao.entity.User;

public class UserSqlProvider extends BaseSqlProvider<User> {
	
	public String findUserByAuth(){
		String sql = "select * from users where user_name = #{userName} and pass_word = #{passWord}";
		return sql;
	}
	
	public String getUserMenu(List<String> userRoles){
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
	
	public String findUserInfoById(){
		String sql = "select * from users where id = #{id}";
		return sql;
	}
}