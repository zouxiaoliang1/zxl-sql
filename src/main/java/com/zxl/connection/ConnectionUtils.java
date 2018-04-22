package com.zxl.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Created by zouxiaoliang on 2018/4/8.
 */
public final class ConnectionUtils {

	private static ComboPooledDataSource dataSource;

	static {
		Properties properties = new Properties();
		InputStream inputStream = ConnectionUtils.class.getResourceAsStream("/config.properties");
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

	public static Connection getConnection() {
		Connection con = null;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}

	public static void closeConnection(Connection con, Statement statement, ResultSet resultSet) {
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
