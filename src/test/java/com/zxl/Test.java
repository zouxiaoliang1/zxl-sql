package com.zxl;

import com.zxl.session.DefaultSqlSession;
import com.zxl.session.SqlSession;

/**
 * Created by zouxiaoliang on 2018/4/8.
 */
public class Test {
	public static void main(String[] args) {
		String resource = "c3p0-config.properties";
		SqlSession sqlSession = new DefaultSqlSession(resource);
		DogMapper dogMapper = sqlSession.getMapper(DogMapper.class);

//		int rows = dogMapper.insert(2L, "cat");
//		System.out.println(rows);

		User user = dogMapper.select();
		System.out.println(user.getId() + " " + user.getName());
//		String sql = "select id, name from table1";
//		User user = sqlSession.selectOne(sql, User.class);
//		System.out.println(user.getId() + " " + user.getName());
	}
}
