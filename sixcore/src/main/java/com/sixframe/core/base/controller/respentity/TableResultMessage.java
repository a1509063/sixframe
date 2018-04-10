package com.sixframe.core.base.controller.respentity;

import java.util.List;

public class TableResultMessage<T> {
	private long code;
	private String msg;
	private long count;
	private List<T> data;
	
	public TableResultMessage(){}
	
	public TableResultMessage(List<T> data){
		this.code = 0L;
		this.count = 1000L;
		this.data = data;
	}
	
	public long getCode() {
		return code;
	}
	public void setCode(long code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
}
