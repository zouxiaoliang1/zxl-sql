package com.zxl.session;

import java.lang.reflect.Proxy;

import com.zxl.executor.DefaultExecutor;
import com.zxl.executor.Executor;
import com.zxl.proxy.MapperHandle;

/**
 * Created by zouxiaoliang on 2018/4/8.
 */
public class DefaultSqlSession implements SqlSession {

	private Executor executor;

	private Configuration configuration;

	public DefaultSqlSession (String resource) {
		this.configuration = new Configuration(resource);
		this.executor = new DefaultExecutor(configuration);
	}

	@Override
	public <T> T selectOne(String sql, Class<T> clazz) {
		return executor.query(sql, clazz);
	}

	/**
	 * 代理纯接口 获取代理类
	 * @param mapperInterface
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T getMapper(Class<T> mapperInterface) {
		MapperHandle handle = new MapperHandle(this);
		ClassLoader loader = mapperInterface.getClassLoader();
		Class<?>[] interfaces = new Class[]{mapperInterface};
		return (T) Proxy.newProxyInstance(loader, interfaces, handle);
	}
}
