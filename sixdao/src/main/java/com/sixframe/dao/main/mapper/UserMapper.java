package com.sixframe.dao.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.sixframe.dao.entity.Authority;
import com.sixframe.dao.entity.User;
import com.sixframe.dao.main.mapper.sqlprovider.UserSqlProvider;

public interface UserMapper {
	
	@SelectProvider(type=UserSqlProvider.class,method="findEntity")
	public User findUser(@Param("user")User user);
	
	/**
	 * 根据名字取得用户信息
	 * @param userName
	 * @return
	 */
	@Select("select * from users where user_name = #{userName}")
	public User findByUsername(String userName);
	/**
	 * 新增用户
	 * @param userName
	 * @return
	 */
	public User insert(User user);
	/**
	 * 用户名密码校验
	 * @param userName
	 * @param passWord
	 * @return
	 */
	@SelectProvider(type=UserSqlProvider.class,method="findUserByAuth")
	public User findUserByAuth(@Param("userName")String userName,@Param("passWord")String passWord);
	
	/**
	 * 获取用户的菜单
	 * @return
	 */
	@SelectProvider(type=UserSqlProvider.class,method="getUserMenu")
	public List<Authority> findUserMenu(List<String> userRoles);
	
	/**
	 * 获取用户完整信息
	 * @param id
	 * @return
	 */
	@Select("select * from users where id = #{id}")
	@Results({
		@Result(property = "id",column="id"),
		@Result(property = "roles",javaType = List.class,column = "id",many = @Many(select = "com.demo.app.dao.main.mapper.RoleMapper.findByRoleUserId"))
	})
	public User findUserInfoById(long id);
	
	/**
	 * 获取用户列表
	 * @param user
	 * @return
	 */
	@Select("select * from users where user_name like concat(concat('%',#{userName}),'%')")
	public List<User> findUserList(User user);
	
}
