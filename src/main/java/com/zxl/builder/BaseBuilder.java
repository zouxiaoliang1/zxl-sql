package com.zxl.builder;

import com.zxl.session.Configuration;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public abstract class BaseBuilder {
	protected Configuration configuration;

	public BaseBuilder() {

	}

	public BaseBuilder(Configuration configuration) {
		this.configuration = configuration;
	}
}
