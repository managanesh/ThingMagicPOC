package com.veda.cache.tasks;

import com.veda.cache.Tag;
import com.veda.cache.utils.DateConvertUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TagInspectorTask {
    private static final Logger logger = Logger.getLogger(TagInspectorTask.class);

    @Autowired
    @Qualifier("cacheObj")
    ConcurrentHashMap tagObjs;


    @Scheduled(fixedRate = 30000)
    public void run() {
        System.out.printf("Strated inspector....");
        HashMap<String, Tag> map = new HashMap();

        map.putAll(tagObjs);
        List validList = new ArrayList<Tag>();
        List invalidList = new ArrayList<Tag>();

        map.forEach((key, tag) -> {

            if (isValidTag(tag)) {
                validList.add(tag);
            } else {
                invalidList.add(tag);
                System.out.println("Identified tag:" + tag + " as invalid and removing from cache");
                tagObjs.remove(key);
            }
        });

        /**
         for (Map.Entry<String, Tag> entry : map.entrySet()) {
         if (isValidTag(entry.getValue())) {
         validList.add(entry.getValue());
         } else {
         invalidList.add(entry.getValue());
         tagObjs.remove(entry.getKey());
         }
         }
         */
        System.out.println("validList" + validList);
        System.out.println("invalidList" + invalidList);

    }

    private boolean isValidTag(Tag tag) {

        boolean isValid = true;

        LocalDateTime scanDateTime = DateConvertUtils.asLocalDateTime(new Date(tag.getScanDate()));
        System.out.println("Tag Scan Date: " + scanDateTime);

        LocalDateTime now = LocalDateTime.now();
        System.out.println("Current Date: " + now);

        LocalDateTime _30secAgo = now.minus(30, ChronoUnit.SECONDS);
        System.out.println("CurrentDate - 30 sec: " + _30secAgo);

        if (scanDateTime.isBefore(_30secAgo)) {
            System.out.println("======tag invalid====: " + tag.getTagId());
            isValid = false;
        } else {
            isValid = true;
        }

        return isValid;
    }

}
