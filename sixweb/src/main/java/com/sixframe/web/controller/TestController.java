package com.sixframe.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
	@RequestMapping("getU")
	public Map<String,Object> test(){
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("user_name", "panfei");
		data.put("menus", "zhizhang");
		return data;
	}
}
