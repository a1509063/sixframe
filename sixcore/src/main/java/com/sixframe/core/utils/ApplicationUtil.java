package com.sixframe.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.sixframe.core.entity.ApplicationProperties;
import com.sixframe.utils.exception.BusinessException;

@Component
public class ApplicationUtil {

	private static final String FILE_NAME = "application.properties";

	public Properties readApplication() throws IOException {
		File dirFile = new File("");
		String url = dirFile.getAbsolutePath();
		url = url + "\\src\\main\\resources\\";
		File file = new File(url + FILE_NAME);
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			if(inStream != null) inStream.close();
		}
	}
	
	public <T> void writeApplication(T entity,String subName) throws IOException {
		String prefx = ApplicationProperties.DATA_SOURCE_PREFIX;
		Field[] fields = entity.getClass().getDeclaredFields();
		File dirFile = new File("");
		String url = dirFile.getAbsolutePath();
		url = url + "\\src\\main\\resources\\";
		File file = new File(url + FILE_NAME);
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file,true);
			outputStream.write("\r\n".getBytes());//写入一个换行
			String remarks = "\r\n#" + entity.getClass().getName();
			outputStream.write(remarks.getBytes());
			String subValue = "";
			if(subName != null) {
				for (Field field : fields) {
					String propertieName = field.getName();
					if(subName.equals(propertieName)) {
						String propertie = propertieName.substring(0,1).toUpperCase() + propertieName.substring(1,propertieName.length());
						Method method = entity.getClass().getMethod("get" + propertie);
						String value = (String) method.invoke(entity);
						subValue = value;
					}
				}
			}
			for (Field field : fields) {
				String propertieName = field.getName();
				if(propertieName.contains("this")) continue;
				String propertie = propertieName.substring(0,1).toUpperCase() + propertieName.substring(1,propertieName.length());
				Method method = entity.getClass().getMethod("get" + propertie);
				String value = (String) method.invoke(entity);
				String item = "\r\n" + prefx + subValue + "." + propertieName + ":" + value;
				outputStream.write(item.getBytes());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new BusinessException(e.getMessage());
		}finally {
			if(outputStream != null) outputStream.close();
		}
	}
}
