package com.zxl;

import com.zxl.annotation.Insert;
import com.zxl.annotation.Param;
import com.zxl.annotation.Select;

/**
 * Created by zouxiaoliang on 2018/4/8.
 */
public interface DogMapper {
	@Select("select id, name from table1")
	User select();

	@Insert("insert into table1(id, name) values(#{id}, #{name})")
	int insert(@Param("id") Long id, @Param("name") String name);
}
