package com.sixframe.dao.entity;

public class Authority {
	private long id;
	private String authorityName;//权限名
	private String authorityDescribe;
	private long parentAuthorityId;
	private String authorityType;
	private String authorityPicture;
	private String authorityUrl;
	private boolean hasChild = false;
	
	public boolean isHasChild() {
		return hasChild;
	}
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAuthorityName() {
		return authorityName;
	}
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}
	public String getAuthorityDescribe() {
		return authorityDescribe;
	}
	public void setAuthorityDescribe(String authorityDescribe) {
		this.authorityDescribe = authorityDescribe;
	}
	public long getParentAuthorityId() {
		return parentAuthorityId;
	}
	public void setParentAuthorityId(long parentAuthorityId) {
		this.parentAuthorityId = parentAuthorityId;
	}
	public String getAuthorityType() {
		return authorityType;
	}
	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}
	public String getAuthorityPicture() {
		return authorityPicture;
	}
	public void setAuthorityPicture(String authorityPicture) {
		this.authorityPicture = authorityPicture;
	}
	public String getAuthorityUrl() {
		return authorityUrl;
	}
	public void setAuthorityUrl(String authorityUrl) {
		this.authorityUrl = authorityUrl;
	}
	
	
}
