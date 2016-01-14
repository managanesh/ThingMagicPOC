package com.veda.cache;

import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import com.veda.cache.config.AppConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTask {
	private static final Logger logger = Logger.getLogger(MainTask.class);
	static ConcurrentHashMap<String, String> cacheObj = new ConcurrentHashMap<String, String>();
	static ConcurrentHashMap<String, Tag> cache  = new ConcurrentHashMap<String, Tag>();

    public static void main(String... args){
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        System.out.println();

    }

	public static void main1(String[] args) {
		/*ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		//ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	    ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
	    TagReader tagReaderTask = (TagReader) context.getBean("tagReader");
	    tagReaderTask.setCache(cacheObj);
	    taskExecutor.execute(tagReaderTask);*/
		/*MainTask mt = new MainTask();
		mt.startTagReader(cacheObj);
		System.out.println("Hello");*/
		TagReader tr = new TagReader();
	//	tr.setCache(cacheObj);
		tr.setName("Tag Reader Thread");
		//tr.setTagObjs(cache);
		tr.run();
		
		
		Timer time = new Timer(); // Instantiate Timer Object
		/*ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
		time.schedule(st, 0, 1000); // Create Repetitively task for every 1 secs
*/		
	/*	TagInspector tins = new TagInspector();
		tins.setCache(cacheObj);
		tins.setCacheObj(cache); 
		tins.setName("Tag Inspector Thread"); 
		time.schedule(tins, 0L, 30000L);
		//tins.run();
	*/
		
		
		

	}

}
