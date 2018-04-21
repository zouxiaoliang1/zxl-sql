package com.zxl.executor;

/**
 * Created by zouxiaoliang on 2018/4/14.
 */
public interface Executor {
	<T> T query(String statement, Class<T> clazz);
}
