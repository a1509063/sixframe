package com.sixframe.dao.entity;

import java.util.Date;
import java.util.List;

public class User {
	private long id;
	private String userName;
	private String passWord;
	private List<Roles> roles;
	private Date lastPasswordResetDate;
	private boolean accountNonExpired;//账户是否未过期
    private boolean accountNonLocked;//账户是否未锁定
    private boolean credentialsNonExpired;//密码是否过期
    private boolean enabled;//账户是否激活
	
    /**
	 * 获取用户可操作菜单
	 * @return
	 */
//	public List<Authority> findUserMenus(){
//		List<Authority> authorities = new ArrayList<Authority>();
//		for (Roles role : roles) {
//			for (Authority authority : role.getAuthorities()) {
//				if(!authorities.contains(authority) && AuthorityType.MENU.toString().equals(authority.getAuthorityType()))
//					authorities.add(authority);
//			}
//		}
//		for (Authority authority : authorities) {
//			for (Authority child : authorities) {
//				if(child.getParentAuthorityId() == authority.getId()){
//					authority.setHasChild(true);
//					break;
//				}
//			}
//		}
//		return authorities;
//	}
	/**
	 * 获取用户除菜单的所有权限
	 * @return
	 */
//	public List<Authority> findUserAuthorities(){
//		List<Authority> authorities = new ArrayList<Authority>();
//		for (Roles role : roles) {
//			for (Authority authority : role.getAuthorities()) {
//				if(!authorities.contains(authority) && !AuthorityType.MENU.toString().equals(authority.getAuthorityType()))
//					authorities.add(authority);
//			}
//		}
//		return authorities;
//	}
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}
	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
