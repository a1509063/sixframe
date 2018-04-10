package com.sixframe.dao.main.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.sixframe.dao.entity.Authority;

public interface AuthorityMapper {
	
	@Select("select a.* from authority a join role_authority b on a.id = b.authority_id where b.role_id = #{id}")
	public List<Authority> findByAuthorityId(String id);
	
	@Select("select * from authority")
	public List<Authority> findAuthorities();
	
	@Select("select * from authority where id = #{id}")
	public Authority findAuthInfoById(long id);
}
