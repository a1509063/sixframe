package com.sixframe.dao.main.mapper.sqlprovider;

import java.util.Map;

import com.sixframe.dao.utils.SqlOperator;

/**
 * 所有sqlprovider可继承该类，实现直接对实体类的操作
 * @author admin
 *
 */
public class BaseSqlProvider<T> {
	
	protected SqlOperator sqlOperatro;
	
	public BaseSqlProvider(){
		sqlOperatro = new SqlOperator();
	}

	public String addEntity(String className,Object entity){
		return null;
	}
	
	public String findEntity(Map<String, T>  data){
		sqlOperatro.clear();
//		T u = data.get("param1");
		sqlOperatro.addSql("select * from");
//		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(u);
//		int len = origDescriptors.length;
		
		return sqlOperatro.toString();
	}
}
