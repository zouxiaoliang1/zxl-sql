package com.zxl.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zxl.annotation.Insert;
import com.zxl.annotation.Param;
import com.zxl.annotation.Select;
import com.zxl.exception.YourBatisException;
import com.zxl.connection.ConnectionUtils;
import com.zxl.session.SqlSession;
import com.zxl.utils.StringUtils;

/**
 * Created by zouxiaoliang on 2018/4/8.
 * 代理接口无实现类
 */
public class MapperHandle implements InvocationHandler {

	private SqlSession sqlSession;

	public MapperHandle(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Select select = method.getAnnotation(Select.class);
		if (select != null) {
			return select(method, select);
		}
		Insert insert = method.getAnnotation(Insert.class);
		if (insert != null) {
			return insert(method, insert, args);
		}
		return null;
	}

	private Object insert(Method method, Insert insert, Object[] args) {
		String sql = insert.value();
		Parameter[] parameters = method.getParameters();
		for (int i = 0; i < parameters.length; i++) {
			Param param = parameters[i].getAnnotation(Param.class);
			if (param == null) {
				throw new YourBatisException("参数注解不能为空");
			}
			String repField = "#{" + param.value() + "}";
			if (args[i] instanceof String) {
				args[i] = "'" + args[i].toString() + "'";
			}
			sql = sql.replace(repField, args[i].toString());
		}
		System.out.println("执行-----" + sql);
		Connection con = ConnectionUtils.getConnection();
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement(sql);
			int rows = statement.executeUpdate();
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtils.closeConnection(con, statement, null);
		}
		return 0;
	}

	private Object select(Method method, Select select) {
		String sql = select.value();
		if (StringUtils.isEmpty(sql)) {
			throw new YourBatisException("注解值为空");
		}
		Class returnType = method.getReturnType();
		Object returnObject = null;
		try {
			returnObject = returnType.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Class returnClazz = returnObject.getClass();

		return sqlSession.selectOne(sql, returnClazz);
	}

}
