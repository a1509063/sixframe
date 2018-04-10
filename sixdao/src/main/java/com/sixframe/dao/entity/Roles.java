package com.sixframe.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class Roles {
	private long id;
	private String roleName;
	private String roleDescribe;
	private List<Authority> authorities;
	
	public List<TreeJson> findAuthsJson(List<TreeJson> allAuths){
		List<TreeJson> returnData = new ArrayList<>();
		for (TreeJson treeJson : allAuths) {
			for (Authority authority : authorities) {
				if(treeJson.getId() == authority.getId()){
					treeJson.setChecked(true);
					break;
				}
			}
			returnData.add(treeJson);
		}
		return returnData;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleDescribe() {
		return roleDescribe;
	}
	public void setRoleDescribe(String roleDescribe) {
		this.roleDescribe = roleDescribe;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	
}
