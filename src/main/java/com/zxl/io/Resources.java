package com.zxl.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by zouxiaoliang on 2018/4/22.
 */
public class Resources {
	private static ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();

	public static Properties getResourceAsProperties(String resource) throws IOException {
		Properties properties = new Properties();
		InputStream inputStream = getResourceAsStream(resource);
		properties.load(inputStream);
		inputStream.close();
		return properties;
	}

	public static InputStream getResourceAsStream(String resource) throws IOException {
		return getResourceAsStream(null, resource);
	}

	public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
		InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
		if (in == null) {
			throw new IOException("Could not find resource " + resource);
		}
		return in;
	}
}
