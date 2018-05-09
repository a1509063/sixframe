package com.sixframe.core.base.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sixframe.core.entity.ApplicationProperties;
import com.sixframe.core.entity.ApplicationProperties.DataSource;
import com.sixframe.core.utils.ApplicationUtil;
import com.sixframe.utils.exception.BusinessException;

@Service
public class DataSourceOperService {
	@Autowired
	private ApplicationUtil applicationUtil;
	
	@Value("${isDev}")
	private boolean isDev=false;
	
	@Value("${nowprofile}")
	private String active="";
	
	@Value("${release.environment}")
	private String environment="";
	
	public Map<String, DataSource> currentDataSource(HttpServletRequest request) {
		Properties properties = null;
		String realPath = null;
		if(isDev) {
			System.out.println("1");
			if("test".equals(active)) {
				System.out.println("1-1");
				if("Linux".equals(environment)) {
					realPath = request.getServletContext().getRealPath("/WEB-INF/classes/application-test.properties");
				}else {
					realPath = request.getServletContext().getRealPath("\\WEB-INF\\classes\\application-test.properties");
				}
			}else {
				System.out.println("1-2");
				File dirFile = new File("");
				String url = dirFile.getAbsolutePath();
				if("Linux".equals(environment)) {
					realPath = url + "/src/main/resources/application-dev.properties";
				}else {
					realPath = url + "\\src\\main\\resources\\application-dev.properties";
				}
			}
		}else {
			System.out.println("2");
			if("Linux".equals(environment)) {
				realPath = request.getServletContext().getRealPath("/WEB-INF/classes/application-prod.properties");
			}else {
				realPath = request.getServletContext().getRealPath("\\WEB-INF\\classes\\application-prod.properties");
			}
		}
		System.out.println("before realPath = " + realPath);
		try {
			properties = applicationUtil.readApplication(realPath);
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
	public void addDataSource(DataSource dataSource,HttpServletRequest request) {
		try {
			String realPath = null;
			if(isDev) {
				File dirFile = new File("");
				String url = dirFile.getAbsolutePath();
				if("Linux".equals(environment)) {
					realPath = url + "/src/main/resources/application-dev.properties";
				}else {
					realPath = url + "\\src\\main\\resources\\application-dev.properties";
				}
			}else {
				if("Linux".equals(environment)) {
					realPath = request.getServletContext().getRealPath("/WEB-INF/classes/application-prod.properties");
				}else {
					realPath = request.getServletContext().getRealPath("\\WEB-INF\\classes\\application-prod.properties");
				}
			}
			
			applicationUtil.writeApplication(dataSource,"dataSourceName",realPath);
		} catch (IOException e) {
			throw new BusinessException(e.getMessage());
		}
	}
}
