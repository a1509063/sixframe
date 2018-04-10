package com.sixframe.core.entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ApplicationProperties {
	
	private final String DATA_SOURCE_PREFIX="spring.datasource.six";
	
	public Map<String,DataSource> dataSources;
	
	public ApplicationProperties(Properties properties) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		dataSources = new HashMap<>();
		Set<Object> keys = properties.keySet();
		for (Iterator<Object> iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(key.indexOf(DATA_SOURCE_PREFIX)!=-1) {
				String dataSourceName = key.substring(key.indexOf(DATA_SOURCE_PREFIX) + DATA_SOURCE_PREFIX.length(),key.lastIndexOf("."));
				DataSource dSource = null;
				if(dataSources.containsKey(dataSourceName)) {
					dSource = dataSources.get(dataSourceName);
				}else {
					dSource = new DataSource();
					dSource.setDataSourceName(dataSourceName);
					dataSources.put(dataSourceName, dSource);
				}
				String propertiyName = key.substring(key.lastIndexOf(".") + 1,key.length());
				String value = properties.getProperty(key);
				this.putPropertieInEntity(propertiyName,value,dSource);
			}
		}
	}
	
	/**数据源类*/
	public class DataSource{
		private String dataSourceName;
		private String driverClassName;
		private String url;
		private String username;
		private String password;
		private String filters;
		
		public String getDataSourceName() {
			return dataSourceName;
		}
		public void setDataSourceName(String dataSourceName) {
			this.dataSourceName = dataSourceName;
		}
		public String getDriverClassName() {
			return driverClassName;
		}
		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFilters() {
			return filters;
		}
		public void setFilters(String filters) {
			this.filters = filters;
		}
		
	}
	
	public <T> void putPropertieInEntity(String propertieName,Object value,T entity) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Field[] fields = entity.getClass().getDeclaredFields();
		for (Field field : fields) {
			if(field.getName().equals(propertieName)) {
				propertieName = propertieName.substring(0,1).toUpperCase() + propertieName.substring(1, propertieName.length());
				Method method = entity.getClass().getMethod("set" + propertieName, field.getType());
				method.invoke(entity,(field.getType().cast(value)));
			}
		}
	}
	
}
