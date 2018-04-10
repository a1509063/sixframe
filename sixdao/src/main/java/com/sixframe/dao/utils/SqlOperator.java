package com.sixframe.dao.utils;

public class SqlOperator {
	
	private final StringBuffer sb;
	
	public SqlOperator(){
		sb = new StringBuffer();
	}
	
	public void addSql(String sql){
		sb.append(" " + sql + " ");
	}
	
	public void clear(){
		sb.delete(0, sb.length());
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
