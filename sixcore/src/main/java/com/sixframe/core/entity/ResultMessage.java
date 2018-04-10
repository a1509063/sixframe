package com.sixframe.core.entity;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

public class ResultMessage {
	
	private String status;//状态码
	private String exception;//异常类型
	private String message;//信息
	private String path;//请求路径
	private Object data;
	
	private final static String EXCEPTION_MESSAGE = "success";
	private final static String MESSAGE = "请求成功";
	private final static String PATH_MESSAGE = "not fund";
	
	public ResultMessage(){
		this(HttpStatus.OK.toString(),EXCEPTION_MESSAGE,MESSAGE,PATH_MESSAGE);
	}
	
	public ResultMessage(Object data) {
		this(HttpStatus.OK.toString(),EXCEPTION_MESSAGE,MESSAGE,PATH_MESSAGE,data);
	}
	
	public  ResultMessage(HttpServletRequest request){
		this(HttpStatus.OK.toString(),EXCEPTION_MESSAGE,MESSAGE,request.getContextPath() + request.getServletPath());
	}
	
	public ResultMessage(Object data,HttpServletRequest request){
		this(HttpStatus.OK.toString(),EXCEPTION_MESSAGE,MESSAGE,request.getContextPath() + request.getServletPath(),data);
	}
	
	public ResultMessage(String status,String exception,String message,String path){
		this.status = status;
		this.exception = exception;
		this.message = message;
		this.path = path;
	}
	
	public ResultMessage(String status,String exception,String message,String path,Object data){
		this.status = status;
		this.exception = exception;
		this.message = message;
		this.path = path;
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	
}
