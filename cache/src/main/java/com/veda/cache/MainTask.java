package com.veda.cache;

import com.veda.cache.config.AppConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTask {
    private static final Logger logger = Logger.getLogger(MainTask.class);

    public static void main(String... args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

    }


}


