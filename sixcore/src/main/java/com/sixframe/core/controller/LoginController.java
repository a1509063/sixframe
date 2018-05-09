package com.sixframe.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sixframe.core.entity.ResultMessage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("login")
@PreAuthorize("hasRole('USER')")
public class LoginController {
	
	@ApiOperation(value="创建用户", notes="根据User对象创建用户")
	@RequestMapping(value = "loginIn", method=RequestMethod.GET)
	public ResultMessage loginIn() {
		Map<String, Object> map = new HashMap<>();
		map.put("userName", "潘飞");
		map.put("passWord", "zhizhanga");
		JSONObject o = JSON.parseObject("{\"zhi\":\"4\"}");
		return new ResultMessage(o);
	}
}
