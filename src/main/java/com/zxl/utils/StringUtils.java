package com.zxl.utils;

/**
 * Created by zouxiaoliang on 2018/4/13.
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {
	public static String firstUpperCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
}
