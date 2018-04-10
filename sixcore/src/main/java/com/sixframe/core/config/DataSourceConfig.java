package com.sixframe.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig implements TransactionManagementConfigurer {
    @Bean(name="base",destroyMethod = "close", initMethod = "init")
	@ConfigurationProperties(prefix = "spring.datasource.sixbase",ignoreUnknownFields = false) // application.properteis中对应属性的前缀
    public DruidDataSource dataSource1() {
    	DruidDataSource druidDataSource = new DruidDataSource();  
        return druidDataSource;
    }
	
    @Bean(name="db2",destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.sixdb2",ignoreUnknownFields = false) // application.properteis中对应属性的前缀
    public DruidDataSource dataSource2() {
    	DruidDataSource druidDataSource = new DruidDataSource(); 
        return druidDataSource;
    }
	
    /**
     * 事务配置
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager1(){
    	return new DataSourceTransactionManager(dataSource1());
    }
    @Bean
    public PlatformTransactionManager txManager2(){
    	return new DataSourceTransactionManager(dataSource2());
    }
    
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager1();
	}
}
