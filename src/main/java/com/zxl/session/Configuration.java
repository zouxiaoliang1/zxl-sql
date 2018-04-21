package com.zxl.session;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public class Configuration {
	private static ClassLoader loader = ClassLoader.getSystemClassLoader();

	private Properties properties;

	/** 单列数据源 */
	private static ComboPooledDataSource dataSource;

	public Configuration() {

	}

	public Configuration(String resource) {
		evalDataSource(resource);
	}

	/**
	 * 从线程池获取连接，避免多线程问题，用双重检验锁
	 * @param resource
	 * @return
	 */
	private static void evalDataSource(String resource) {
		if (dataSource == null) {
			synchronized (Configuration.class) {
				if (dataSource == null) {
					Properties properties = new Properties();
					InputStream inputStream = loader.getResourceAsStream(resource);
					try {
						properties.load(inputStream);
						dataSource = new ComboPooledDataSource("mysql");
						dataSource.setDriverClass(properties.getProperty("driverClass"));
						dataSource.setUser(properties.getProperty("user"));
						dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
						dataSource.setInitialPoolSize(new Integer(properties.getProperty("initialPoolSize")));
						dataSource.setMaxPoolSize(new Integer(properties.getProperty("maxPoolSize")));
						dataSource.setMinPoolSize(new Integer(properties.getProperty("minPoolSize")));
						dataSource.setMaxIdleTime(new Integer(properties.getProperty("maxIdleTime")));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public Connection getConnection() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public void closeConnection(Connection con, Statement statement, ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
