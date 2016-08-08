package com.luxbet.qa.framework.utils;

/**
 * Timer class can be included in the scripts.
 * add the start/end markers between your test steps and the Timer object will do the rest.
 * You can also pass the timer instance to other methods/classes and calculate deltaTimes from inside the classes.
 * Created by ashoka on 1/04/2016.
 */
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Date;

public class Timer {

    private long startTime = 0;
    private long endTime = 0;

    private static Logger logger = Logger.getLogger(StringHelper.class);

    public void start() throws IOException {
        this.startTime = System.currentTimeMillis();
        logger.info("TIMER START -> " + this.dateParser(this.startTime));
    }


    public void end() throws IOException {
        this.endTime = System.currentTimeMillis();
        logger.info("TIMER END -> " + this.dateParser(this.endTime));
    }


    public Date getStartTime() throws IOException {
        logger.info("GET START -> " + this.dateParser(this.startTime));
        return this.dateParser(this.startTime);
    }


    public Date getEndTime() throws IOException {
        logger.info("GET END -> " + this.dateParser(this.endTime));
        return this.dateParser(this.endTime);
    }


    public long getTotalTime() throws IOException {
        long deltaTime = this.endTime - this.startTime;
        logger.info("GET TOTAL -> "+deltaTime+" ms");
        return deltaTime;
    }


    public Date dateParser(long unixTime){
        Date date = new Date ();
        date.setTime(unixTime);
        return date;
    }
}
