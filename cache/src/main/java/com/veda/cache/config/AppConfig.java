package com.veda.cache.config;

import com.veda.cache.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
@ComponentScan(basePackages = "com.veda.cache")
@EnableAsync
@EnableScheduling
public class AppConfig {


   @Bean(name = "cacheObj")
    public ConcurrentHashMap<String, String> getCacheObj() {
        return new ConcurrentHashMap<String, String>();

    }

    @Bean(name = "cacheMap")
    public ConcurrentHashMap<String, Tag> getCache() {
        return new ConcurrentHashMap<String, Tag>();

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
