package ${package_name}.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfig implements TransactionManagementConfigurer {
<#list model.sources as source>
	@Bean(name="${source.name}",destroyMethod = "close", initMethod = "init")
	@ConfigurationProperties(prefix = "spring.datasource.${source.name}",ignoreUnknownFields = false) // application.properteis中对应属性的前缀
    public DruidDataSource dataSource${source.name}() {
    	DruidDataSource druidDataSource = new DruidDataSource();  
        return druidDataSource;
    }
    
    @Bean
    public PlatformTransactionManager txManager${source.name}(){
    	return new DataSourceTransactionManager(dataSource${source.name}());
    }
</#list>
    /**
     * 事务配置
     * @return
     */
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return txManager${model.default}();
	}
}