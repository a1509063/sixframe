package com.sixframe.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.sixframe.core.entity.ApplicationProperties;

@Component
public class ApplicationUtil {

	private static final String FILE_NAME = "application.properties";

	public Properties readApplication() throws IOException {
		File dirFile = new File("");
		String url = dirFile.getAbsolutePath();
		url = url + "\\src\\main\\resources\\";
		File file = new File(url + FILE_NAME);
		InputStream inStream = null;
		inStream = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(inStream);
		return properties;
	}
	
	public <T> void writeApplication(T entity) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Properties properties = this.readApplication();
		String prefx = ApplicationProperties.DATA_SOURCE_PREFIX;
		Field[] fields = entity.getClass().getDeclaredFields();
		File dirFile = new File("");
		String url = dirFile.getAbsolutePath();
		url = url + "\\src\\main\\resources\\";
		File file = new File(url + FILE_NAME);
		OutputStream outputStream = null;
		outputStream = new FileOutputStream(file);
		for (Field field : fields) {
			String propertieName = field.getName();
			String p = propertieName.substring(0,1).toUpperCase();
			Method method = entity.getClass().getMethod("get" + p, field.getType());
			String value = (String) method.invoke(entity);
			properties.put(prefx + "." + propertieName, value);
		}
	}
}
