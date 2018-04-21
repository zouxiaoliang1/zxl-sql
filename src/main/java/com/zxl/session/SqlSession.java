package com.zxl.session;

/**
 * Created by zouxiaoliang on 2018/4/14.
 */
public interface SqlSession {
	<T> T selectOne(String sql, Class<T> clazz);

	<T> T getMapper(Class<T> mapperInterface);
}
