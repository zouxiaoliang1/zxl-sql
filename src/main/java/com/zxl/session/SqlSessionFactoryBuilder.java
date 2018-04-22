package com.zxl.session;

import java.io.InputStream;
import java.util.Properties;

import com.zxl.builder.xml.XMLConfigBuilder;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public class SqlSessionFactoryBuilder {

	public SqlSessionFactory build(InputStream inputStream) {
		return this.build(inputStream, null, null);
	}

	public SqlSessionFactory build(InputStream inputStream, String environment, Properties properties) {
		XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, environment, properties);
		Configuration configuration = parser.parse();
		return this.build(configuration);
	}

	private SqlSessionFactory build(Configuration config) {
		return new DefaultSqlSessionFactory(config);
	}
}
