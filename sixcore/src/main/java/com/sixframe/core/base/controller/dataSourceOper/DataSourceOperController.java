package com.sixframe.core.base.controller.dataSourceOper;

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
import com.sixframe.utils.exception.BusinessException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 数据源管理
 * @author fengpb
 */
@Api(tags= "数据源管理")
@RestController
@RequestMapping("dataSource")
public class DataSourceOperController {
	
	@Autowired
	private DataSourceOperService dataSourceOperService;
	
	/**
	 * 首页跳转
	 * @param request
	 * @return
	 */
	@ApiOperation(value="首页跳转")
	@RequestMapping(value="index",method=RequestMethod.GET)
	public ResultMessage index(HttpServletRequest request){
		return new ResultMessage(request);
	}
	
	/**
	 * 获取当前数据源列表
	 * @return
	 */
	@ApiOperation(value="获取当前数据源列表")
	@RequestMapping(value = "currentDataSource",method = RequestMethod.GET)
	public TableResultMessage<DataSource> currentDataSource(HttpServletRequest request) {
		Map<String, DataSource> dataSources = dataSourceOperService.currentDataSource(request);
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
	/**
	 * 获取数据源详情
	 * @return
	 */
	@ApiOperation(value="获取当前数据源列详情")
	@ApiImplicitParam(value="数据源名",required=true,name="dataSourceName")
	@RequestMapping(value = "dataSourceInfo",method = RequestMethod.GET)
	public ResultMessage dataSourceInfo(String dataSourceName,HttpServletRequest request) {
		Map<String, DataSource> dataSources = dataSourceOperService.currentDataSource(request);
		Iterator<String> iterator = dataSources.keySet().iterator();
		while (iterator.hasNext()) {
			DataSource dataSource = dataSources.get(iterator.next());
			if(dataSourceName.equals(dataSource.getDataSourceName())){
				return new ResultMessage(dataSource);
			}
		}
		throw new BusinessException("无法查到对应数据源：" + dataSourceName);
	}
	/**
	 * 添加数据源
	 * @return
	 */
	@ApiOperation(value="添加数据源")
	@RequestMapping(value = "addDataSource",method = RequestMethod.POST)
	public ResultMessage addDataSource(DataSource dataSource,HttpServletRequest request) {
		dataSourceOperService.addDataSource(dataSource,request);
		return new ResultMessage();
	}
}
