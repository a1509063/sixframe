package com.sixframe.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

@Component
public class ApplicationUtil {

//	private final String url = "D:/workspaces/oxygen/sixframe/sixcore/src/main/resources/";
	private static final String FILE_NAME = "application.properties";

	public Properties readApplication() throws IOException {
		File dirFile = new File("");
		String url = dirFile.getAbsolutePath();
		System.out.println(url);
		url = url + "\\src\\main\\resources\\";
		File file = new File(url + FILE_NAME);
		InputStream inStream = null;
		inStream = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(inStream);
		return properties;
	}
}
