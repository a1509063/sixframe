package com.sixframe.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.sixframe.dao.impl.CustomSqlSessionTemplate;

@Configuration
@MapperScan(basePackages = "com.sixframe.dao.**.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
public class MybatisDbConfig {
	
	private static final Logger log = LoggerFactory.getLogger(MybatisDbConfig.class);
	
	@Resource(name = "base")
	private DruidDataSource dataSource1;
	
	@Resource(name = "db2")
	private DruidDataSource dataSource2;
	
	/**
	 * 是否自动映射驼峰规则属性
	 */
	@Value("${mybatis.configuration.mapUnderscoreToCamelCase}")
	private boolean mapUnderscoreToCamelCase;
	
    @Bean
    public SqlSessionFactory sqlSessionFactory1() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource1); // 使用base数据源, 连接base库
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);//设置是否自动映射驼峰规则属性
        configuration.setCallSettersOnNulls(true);//设置查询字段为空时，resultMap中可以显示
        configuration.addMappers("com.demo.app.dao.main.mapper");
        factoryBean.setConfiguration(configuration);
        String dataType = dataSource1.getConnection().getMetaData().getDatabaseProductName();//链接数据库类型
        log.debug("当前数据库类型为{}",dataType);
        factoryBean.setPlugins(plugins(dataType));
        return factoryBean.getObject();
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory2() throws Exception {
    	SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
    	factoryBean.setDataSource(dataSource2); // 使用db2数据源, 连接db2库
    	org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
    	configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);//设置是否自动映射驼峰规则属性
    	configuration.setCallSettersOnNulls(true);//设置查询字段为空时，resultMap中可以显示
    	configuration.addMappers("com.demo.app.dao.db2.mapper");
    	factoryBean.setConfiguration(configuration);
    	String dataType = dataSource2.getConnection().getMetaData().getDatabaseProductName();//链接数据库类型
    	log.debug("当前数据库类型为{}",dataType);
    	factoryBean.setPlugins(plugins(dataType));
    	return factoryBean.getObject();
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception{
    	CustomSqlSessionTemplate cst = new CustomSqlSessionTemplate(sqlSessionFactory1());
    	Map<Object, SqlSessionFactory> stMap = new HashMap<Object, SqlSessionFactory>(5);
    	stMap.put("base", sqlSessionFactory1());
    	stMap.put("ds2", sqlSessionFactory2());
    	
    	cst.setTargetSqlSessionFactorys(stMap);
		return cst;
    }
    
    public PageInterceptor[] plugins(String dataType){
    	PageInterceptor p = new PageInterceptor();
    	Properties properties = new Properties();
    	properties.setProperty("reasonable", "true");
    	properties.setProperty("supportMethodsArguments", "true");
    	properties.setProperty("autoRuntimeDialect", "true");
    	properties.setProperty("helperDialect", dataType.toLowerCase());
    	p.setProperties(properties);
    	PageInterceptor[] plugins = {p};
    	return plugins;
    }
}
