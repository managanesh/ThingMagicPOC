package com.veda.cache.tasks;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.veda.cache.Tag;
import com.veda.cache.dao.TagRepositoryImpl;
import com.veda.cache.model.ShelfInv;
import com.veda.cache.utils.DateConvertUtils;

@Component
public class TagInspectorTask {
    private static final Logger logger = Logger.getLogger(TagInspectorTask.class);
    
    @Autowired
    @Qualifier("cacheObj")
    ConcurrentHashMap tagObjs;
	@Autowired
	TagRepositoryImpl repo;

    @Scheduled(fixedRate = 30000)
    @Transactional
    public void run() {
        System.out.printf("Strated inspector....");
        HashMap<String, Tag> map = new HashMap();

        map.putAll(tagObjs);
        List<Tag> validList = new ArrayList<Tag>();
        List<Tag> invalidList = new ArrayList<Tag>();
        List<ShelfInv> sisValid  = new ArrayList<ShelfInv>();
        List<ShelfInv> sisInValid  = new ArrayList<ShelfInv>();


        map.forEach((key, tag) -> {

            if (isValidTag(tag)) {
                validList.add(tag);
            } else {
                invalidList.add(tag);
                System.out.println("Identified tag:" + tag + " as invalid and removing from cache");
              //  tagObjs.remove(key);
            }
        });
       
        
        sisValid = validList.stream()
        		.map((Tag tag) -> convertTagtoShelfInv(tag,"Y"))
        		.collect(Collectors.toList());
        //sis = validList.stream().map(this::convertTagtoShelfInv).collect(Collectors.toList());
        sisInValid = invalidList.stream()
        		.map((Tag tag) -> convertTagtoShelfInv(tag,"N"))
        		.collect(Collectors.toList());
        
        System.out.println("callind updateshelf"+sisValid.size());
       try {
			repo.updateShelfInv(sisValid);
			repo.updateShelfInvInvalid(sisInValid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       // updateDB(validList, invalidList);

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
    
 
private ShelfInv convertTagtoShelfInv(Tag tag,String status){
	ShelfInv si = new ShelfInv();
	si.setRfId(tag.getTagId()); 
	si.setItemStatus(status);
	si.setItemAddedBy("ADMIN");
	si.setItemAddDate(new java.sql.Date(tag.getScanDate()));
	si.setLastUser("ADMIN");
	si.setLastUpdttm(new java.sql.Date(tag.getScanDate()));
	return si;
}
    private boolean updateDB(List<Tag> validTags, List<Tag> inValidTags){
    	
    	return false;
    }
    
    private boolean isValidTag(Tag tag) {

        boolean isValid = true;

        LocalDateTime scanDateTime = DateConvertUtils.asLocalDateTime(new Date(tag.getScanDate()));
        
        System.out.println(tag.getTagId()+" :Tag Scan Date: " + scanDateTime);

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
