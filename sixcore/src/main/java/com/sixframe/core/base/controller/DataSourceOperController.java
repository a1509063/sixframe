package com.sixframe.core.base.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sixframe.core.base.controller.respentity.TableResultMessage;
import com.sixframe.core.base.service.DataSourceOperService;
import com.sixframe.core.entity.ApplicationProperties.DataSource;
import com.sixframe.core.entity.ResultMessage;

/**
 * 数据源管理
 * @author fengpb
 */
@RestController
@RequestMapping("dataSource")
public class DataSourceOperController {
	
	@Autowired
	private DataSourceOperService dataSourceOperService;
	
	@RequestMapping("test")
	public ResultMessage test() {
		return new ResultMessage();
	}
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value="index",method=RequestMethod.GET)
	public ResultMessage index(HttpServletRequest request){
		return new ResultMessage(request);
	}
	
	/**
	 * 获取当前数据源列表
	 * @return
	 */
	@RequestMapping(value = "currentDataSource",method = RequestMethod.GET)
	public TableResultMessage<DataSource> currentDataSource() {
		Map<String, DataSource> dataSources = dataSourceOperService.currentDataSource();
		List<DataSource> list = new ArrayList<>();
		Iterator<String> iterator = dataSources.keySet().iterator();
		while (iterator.hasNext()) {
			list.add(dataSources.get(iterator.next()));
		}
		TableResultMessage<DataSource> tm = new TableResultMessage<>();
		tm.setCount(1L);
		tm.setData(list);
		return tm;
	}
	
	@RequestMapping(value = "dataSourceInfo",method = RequestMethod.GET)
	public ResultMessage dataSourceInfo() {
		Map<String, DataSource> dataSources = dataSourceOperService.currentDataSource();
		List<DataSource> list = new ArrayList<>();
		Iterator<String> iterator = dataSources.keySet().iterator();
		while (iterator.hasNext()) {
			list.add(dataSources.get(iterator.next()));
		}
		return new ResultMessage(list);
	}
}
