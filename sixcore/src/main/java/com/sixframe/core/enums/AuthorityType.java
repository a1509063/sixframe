package com.sixframe.core.enums;

public enum AuthorityType {
	MENU(1,"菜单"),
	BUTTON(2,"按钮");
	
	private final int value;

	private final String authorityDescribe;

	AuthorityType(int value, String authorityDescribe) {
		this.value = value;
		this.authorityDescribe = authorityDescribe;
	}
	
	public int value() {
		return this.value;
	}
	
	public String getAuthorityDescribe() {
		return this.authorityDescribe;
	}
	
	@Override
	public String toString() {
		return Integer.toString(this.value);
	}
	
	public static AuthorityType valueOf(int typeCode) {
		for (AuthorityType types : values()) {
			if (types.value == typeCode) {
				return types;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + typeCode + "]");
	}
}
