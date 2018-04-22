package com.zxl.io;

import java.io.InputStream;

/**
 * Created by zouxiaoliang on 2018/4/22.
 */
public class ClassLoaderWrapper {
	ClassLoader defaultClassLoader;
	ClassLoader systemClassLoader;

	public ClassLoaderWrapper() {
		try {
			systemClassLoader = ClassLoader.getSystemClassLoader();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public InputStream getResourceAsStream(String resource, ClassLoader loader) {
		return this.getResourceAsStream(resource, getClassLoader(loader));
	}

	private ClassLoader[] getClassLoader(ClassLoader classLoader) {
		return new ClassLoader[] {
				classLoader,
				defaultClassLoader,
				Thread.currentThread().getContextClassLoader(),
				this.getClass().getClassLoader(),
				systemClassLoader
		};
	}

	InputStream getResourceAsStream(String resource, ClassLoader[] classLoaders) {
		for (ClassLoader cl : classLoaders) {
			if (null != cl) {
				InputStream returnValue = cl.getResourceAsStream(resource);
				if (null == returnValue) {
					returnValue = cl.getResourceAsStream("/" + resource);
				}
				if (null != returnValue) {
					return returnValue;
				}
			}
		}
		return null;
	}
}
