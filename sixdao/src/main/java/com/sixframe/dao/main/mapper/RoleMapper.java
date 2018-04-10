package com.sixframe.dao.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;

import com.sixframe.dao.entity.Roles;
import com.sixframe.dao.main.mapper.sqlprovider.RoleSqlProvider;

public interface RoleMapper {
	
	/**
	 * 根据ID查询角色详情
	 * @param id
	 * @return
	 */
	@Select("select * from role where id = #{id}")
	@Results({
		@Result(property = "id",column="id"),
		@Result(property = "authorities",javaType = List.class,column = "id",many = @Many(select = "com.demo.app.dao.main.mapper.AuthorityMapper.findByAuthorityId"))
	})
	public Roles findByRoleId(long id);
	
	/**
	 * 根据用户ID查询用户的角色详情
	 * @param id
	 * @return
	 */
	@Select("select a.* from role a join user_role b on a.id = b.role_id where b.user_id = #{id}")
	@Results({
		@Result(property = "authorities",javaType = List.class,column = "id",many = @Many(select = "com.demo.app.dao.main.mapper.AuthorityMapper.findByAuthorityId"))
	})
	public List<Roles> findByRoleUserId(long id);
	
	/**
	 * 查询角色列表
	 * @param role
	 * @return
	 */
	@SelectProvider(method="findRoles",type=RoleSqlProvider.class)
	public List<Roles> findRoles(Roles role);
	
	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	@Update("update role set role_name = #{roleName},role_describe = #{roleDescribe} where id = #{id}")
	public int updateRoleInfo(Roles role);
	
	@Delete("delete from role_authority where role_id = #{roleId}")
	public int deleteRoleAuth(long roleId);
	
	@InsertProvider(type=RoleSqlProvider.class,method="addRoleAuth")
	public int addRoleAuth(@Param("role")Roles role);
	
}
