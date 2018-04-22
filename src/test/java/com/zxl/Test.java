package com.zxl;

import java.io.IOException;
import java.io.InputStream;

import com.zxl.io.Resources;
import com.zxl.session.SqlSession;
import com.zxl.session.SqlSessionFactory;
import com.zxl.session.SqlSessionFactoryBuilder;

/**
 * Created by zouxiaoliang on 2018/4/8.
 */
public class Test {
	public static void main(String[] args) throws IOException {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		String resource = "yourbatis-config.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		SqlSessionFactory factory = builder.build(inputStream);
		SqlSession sqlSession = factory.openSession();
		User user = sqlSession.selectOne("select id, name from table1", User.class);
		System.out.println(user.getId() + "  " + user.getName());
		System.out.println(Test.class.getResource(""));
		System.out.println(Test.class.getResource("/"));
	}
}
