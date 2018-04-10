package com.sixframe.dao.main.mapper.sqlprovider;

import java.text.MessageFormat;
import java.util.Map;

import com.sixframe.dao.entity.Roles;

public class RoleSqlProvider extends BaseSqlProvider<Roles> {
	
	public String findRoles(Roles role){
		StringBuffer sb = new StringBuffer();
		sb.append("select * from role ");
		sb.append("where 1=1 ");
		if(role.getId() != 0)
			sb.append("and id = #{id} ");
		if(role.getRoleName() != null)
			sb.append("and role_name = #{roleName}");
		if(role.getRoleDescribe() != null){
			sb.append("and role_describe = #{roleDescribe}");
		}
		return sb.toString();
	}
	
	public String addRoleAuth(Map<String, Object> data){
		sqlOperatro.clear();
		Roles role = (Roles)data.get("role");
		sqlOperatro.addSql("insert into role_authority values");
		MessageFormat mf = new MessageFormat("(#'{'role.id},#'{'role.authorities[{0}].id})");
		for (int i = 0; i < role.getAuthorities().size(); i++) {
			sqlOperatro.addSql(mf.format(new Object[]{i}));
			if(i < role.getAuthorities().size() - 1){
				sqlOperatro.addSql(",");
			}
		}
		return sqlOperatro.toString();
	}
	
}
