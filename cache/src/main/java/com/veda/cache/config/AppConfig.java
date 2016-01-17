package com.veda.cache.config;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.veda.cache.Tag;
import com.veda.cache.model.ShelfInv;

@Configuration
@ComponentScan(basePackages = "com.veda.cache")
@EnableAsync
@EnableScheduling
@EnableTransactionManagement
public class AppConfig {


   @Bean(name = "cacheObj")
    public ConcurrentHashMap<String, String> getCacheObj() {
        return new ConcurrentHashMap<String, String>();

    }
   
   @Bean(name = "shelfInv")
   public ShelfInv getShelfInv(){
	   return new ShelfInv();
   }

    @Bean(name = "cacheMap")
    public ConcurrentHashMap<String, Tag> getCache() {
        return new ConcurrentHashMap<String, Tag>();

    }

    @Bean
	public BasicDataSource dataSource(){
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/myschema");
		dataSource.setUsername("root");
		//dataSource.setPassword("");
		dataSource.setDefaultAutoCommit(true);
 
		return dataSource;
	}
 
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(){
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSource());
 
		return dataSourceTransactionManager;
	}

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

}
