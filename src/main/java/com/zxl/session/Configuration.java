package com.zxl.session;

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

	private Properties properties;

	/** 单列数据源 */
	private static ComboPooledDataSource dataSource;

	public Configuration() {

	}

	public Configuration(Properties properties) {
		this.properties = properties;
		evalDataSource();
	}

	/**
	 * 从线程池获取连接，避免多线程问题，用双重检验锁
	 * @return
	 */
	private void evalDataSource() {
		if (dataSource == null) {
			synchronized (Configuration.class) {
				if (dataSource == null) {
					try {
						dataSource = new ComboPooledDataSource("mysql");
						dataSource.setDriverClass(properties.getProperty("driver"));
						dataSource.setUser(properties.getProperty("user"));
						dataSource.setUser(properties.getProperty("password"));
						dataSource.setJdbcUrl(properties.getProperty("url"));
//						dataSource.setInitialPoolSize(new Integer(properties.getProperty("initialPoolSize")));
//						dataSource.setMaxPoolSize(new Integer(properties.getProperty("maxPoolSize")));
//						dataSource.setMinPoolSize(new Integer(properties.getProperty("minPoolSize")));
//						dataSource.setMaxIdleTime(new Integer(properties.getProperty("maxIdleTime")));
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
}
