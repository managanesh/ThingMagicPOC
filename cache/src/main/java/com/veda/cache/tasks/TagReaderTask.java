package com.veda.cache.tasks;

import com.thingmagic.*;
import com.veda.cache.MainTask;
import com.veda.cache.Tag;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

@Component
@DependsOn({"cacheMap", "cacheObj"})
public class TagReaderTask {
    private static final Logger logger = Logger.getLogger(MainTask.class);

    String name;

    @Autowired
    @Qualifier("cacheMap")
    ConcurrentHashMap cache;

    @Autowired
    @Qualifier("cacheObj")
    ConcurrentHashMap tagObjs;

    Reader reader = null;

    @PostConstruct
    public void init() {
        String readerURI = "tmr://104.34.225.142";
        try {
            reader = Reader.create(readerURI);
            System.out.println("readerURI: " + readerURI);
            reader.connect();
            SimpleReadPlan plan = new SimpleReadPlan(null, TagProtocol.GEN2, null, null, 1000);
            reader.paramSet(TMConstants.TMR_PARAM_READ_PLAN, plan);

            reader.addReadExceptionListener(getReadExceptionListener());

            reader.addReadListener(getReadListener());

            run();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ReadListener getReadListener() {
        return new ReadListener() {

            public void tagRead(Reader reader, TagReadData tagReadData) {
                Tag tag = new Tag();
                String epcString = tagReadData.getTag().epcString();
                tag.setTagId(epcString);
                tag.setScanDate(tagReadData.getTime());
                Tag tagOld = (Tag) tagObjs.put(epcString, tag);
                if (tagOld == null) {
                    System.out.println("New Tag found..." + tag.getTagId());
                }


            }
        };
    }

    @Async
    public void run() {
        reader.startReading();
    }

    @PreDestroy
    public void destroy() {
        reader.stopReading();
    }

    public ReadExceptionListener getReadExceptionListener() {

        return new ReadExceptionListener() {
            public void tagReadException(com.thingmagic.Reader r, ReaderException re) {
                String strDateFormat = "M/d/yyyy h:m:s a";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                String format = sdf.format(Calendar.getInstance().getTime());
                System.out.println("Reader Exception: " + re.getMessage() + " Occured on :" + format);
                if (re.getMessage().equals("Connection Lost")) {
                    System.exit(1);
                }
            }
        };
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCache(ConcurrentHashMap<String, String> cache) {
        this.cache = cache;
    }


}
