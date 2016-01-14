package com.veda.cache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TagInspector {
    private static final Logger logger = Logger.getLogger(TagInspector.class);

    @Autowired
    @Qualifier("cacheObj")
    ConcurrentHashMap tagObjs;


    private static boolean isValidTag(Tag tag) {
        boolean isValid = true;
        long longDate = tag.getScanDate();
        Long secAgo = 30000L;
        //DateTime dateTimeParis = new DateTime( milliseconds ).withZone( DateTimeZone.forID( "Europe/Paris" ) );
        Date date = new Date(longDate);
        System.out.println("Tag Scan Date: " + date);
        Date currentDate = new Date();
        System.out.println("Current Date: " + currentDate);
        Date newDate = new Date(currentDate.getTime() - secAgo);
        System.out.println("CurrentDate - 30 sec: " + newDate);
        if (date.getTime() < newDate.getTime()) {
            System.out.println("======tag invalid====: " + tag.getTagId());
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }


    @Scheduled(fixedRate = 30000)
    public void run() {
        System.out.printf("Strated inspector....");
        HashMap<String, Tag> map = new HashMap();

        map.putAll(tagObjs);
        List validList = new ArrayList<Tag>();
        List invalidList = new ArrayList<Tag>();
        for (Map.Entry<String, Tag> entry : map.entrySet()) {
            if (isValidTag(entry.getValue())) {
                validList.add(entry.getValue());
            } else {
                invalidList.add(entry.getValue());
                tagObjs.remove(entry.getKey());
            }
        }

        System.out.println("validList"+validList);
        System.out.println("invalidList" + invalidList);

    }



/*
    @Override
	public void run0() {
		// TODO Auto-generated method stub
		System.out.println("==========START "+name + " ==========");
		System.out.println("cacheObj isze: "+cacheObj.size());
		*//*Iterator<String> reader=cache.keySet().iterator();
        while(reader.hasNext()){
			String s = reader.next();
			System.out.println("s: "+s);
		}*//*

		Iterator<String> reader1=cacheObj.keySet().iterator();
		while(reader1.hasNext()){
			String key = reader1.next();
			Tag tag = cacheObj.get(key);
			if(isValidTag(tag)){
				
			}
			System.out.println("key: "+key);
			System.out.println("scanDate: "+tag.getScanDate());
		}
		System.out.println("==========END "+name + " ==========");

	}*/

}
