package com.sixframe.core.base.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sixframe.core.entity.ApplicationProperties;
import com.sixframe.core.entity.ApplicationProperties.DataSource;
import com.sixframe.core.utils.ApplicationUtil;
import com.sixframe.utils.exception.BusinessException;

@Service
public class DataSourceOperService {
	@Autowired
	private ApplicationUtil applicationUtil;
	
	public Map<String, DataSource> currentDataSource() {
		Properties properties = null;
		try {
			properties = applicationUtil.readApplication();
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}
		if(properties == null) {
			throw new BusinessException("配置文件未获取！");
		}
		ApplicationProperties aProperties = null;
		try {
			aProperties = new ApplicationProperties(properties);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return aProperties.dataSources;
	}
	/**
	 * 新增数据源
	 * @param dataSource
	 */
	public void addDataSource(DataSource dataSource) {
		try {
			applicationUtil.writeApplication(dataSource,"dataSourceName");
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		}
	}
}
