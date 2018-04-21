package com.zxl.executor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.zxl.connection.ConnectionUtils;
import com.zxl.exception.YourBatisException;
import com.zxl.session.Configuration;
import com.zxl.utils.SqlParseUtils;
import com.zxl.utils.StringUtils;

/**
 * Created by zouxiaoliang on 2018/4/14.
 */
public class DefaultExecutor implements Executor {

	private Configuration configuration;

	public DefaultExecutor(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public <T> T query(String sql, Class<T> clazz) {
		Connection con = configuration.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = con.prepareStatement(sql);
			resultSet = statement.executeQuery();
			String[] fieldNames = SqlParseUtils.parseSelectSql(sql);
			T t = clazz.newInstance();
			while (resultSet.next()) {
				for (String fieldName : fieldNames) {
					Field field = clazz.getDeclaredField(fieldName);
					if (field == null) {
						throw new YourBatisException("字段" + fieldName + "不存在");
					}
					Class fieldClazz = field.getType();
					Method fieldMethod = clazz.getDeclaredMethod("set" + StringUtils.firstUpperCase(fieldName), fieldClazz);
					fieldMethod.invoke(t, resultSet.getObject(fieldName));
				}
			}
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con, statement, resultSet);
		}

		return null;
	}
}
