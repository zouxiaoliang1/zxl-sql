package com.zxl.session;

import java.io.InputStream;

import com.zxl.xml.XMLConfigBuilder;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public class SqlSessionFactoryBuilder {

	public SqlSessionFactory build(InputStream inputStream) {
		XMLConfigBuilder parser = new XMLConfigBuilder(inputStream);
		Configuration configuration = parser.parse();
		return this.build(configuration);
	}

	private SqlSessionFactory build(Configuration config) {
		return new DefaultSqlSessionFactory(config);
	}
}
