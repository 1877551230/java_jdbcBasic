package cn.tedu.dao.common;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
	//此方法专门用来做结果集和实体对象的映射关系
	public T mapRow(ResultSet rs)throws SQLException;
		
	
}
