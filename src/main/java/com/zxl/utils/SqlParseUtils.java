package com.zxl.utils;

import com.zxl.exception.YourBatisException;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public class SqlParseUtils {
	public static String[] parseSelectSql(String sql) {
		int startIndex = Math.max(sql.indexOf("select"), sql.indexOf("SELECT"));
		int endIndex = Math.max(sql.indexOf("from"), sql.indexOf("FROM"));
		if (startIndex < 0 || endIndex < 0) {
			throw new YourBatisException("sql格式错误");
		}
		String fieldSql = sql.substring(startIndex + "select".length(), endIndex).replace(" ", "");
		String[] fields = fieldSql.split(",");
		return fields;
	}
}
