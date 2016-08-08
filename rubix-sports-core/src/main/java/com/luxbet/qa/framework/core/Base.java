package com.luxbet.qa.framework.core;

import org.apache.log4j.*;
//import org.openqa.selenium.browserlaunchers.Sleeper;
import org.testng.Reporter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Selva Nakuladeva on 5/03/2015.
 */
public abstract class Base {
    private static Logger logger = Logger.getLogger(Base.class);
    private static boolean _log4jInitialised = false;
    private static String _logpath = "C:\\automation\\logs\\";
    private static String _logfilename = null;
    private static Level _loggerlevel = Level.INFO;
    private static String _loggerlayout = "%d [%t] %-5p %c %x - %m%n";
    private static boolean _initialised = false;

    public Base() {
        if(!this._initialised) {
            initialiseLogFile();
            initialiseLog4jLogger();
            this._initialised = true;
        }
    }

    /**
     * Description:
     * Initialise log4j allowing all levels to be logged to the console.
     * change the logger level here
     * @author Selva Nakuladeva
     * @since 23 July 2014
     */
    protected void initialiseLog4jLogger() {
        if(!_log4jInitialised) {
            //This is the root logger provided by log4j
            Logger rootLogger = Logger.getRootLogger();
            rootLogger.setLevel(getLoggerLevel());
            PatternLayout layout = new PatternLayout(getLoggerPatternLayout());

            //Add console appender to root logger
            rootLogger.addAppender(new ConsoleAppender(layout));
            try
            {
                //Define file appender with layout and output log file name
                FileAppender fileAppender = new FileAppender(layout, getLogPath() + getLogFileName(), false);
                //Add the appender to root logger
                rootLogger.addAppender(fileAppender);
                _log4jInitialised = true;
                logger.info("Log4j logger initialised.");
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void initialiseLogFile() {
        if(getLogFileName() == null) {
            setLogFileName("testrunlog"+new SimpleDateFormat("_dd_MM_yyyy__HHmmss").format(new Date())+".txt");
        }
        Reporter.log("For detailed Log, see : " + getLogPath() + getLogFileName());
    }

    protected String getLogPath() {
        return this._logpath;
    }

    protected void setLogPath(String logPath) {
        this._logpath = logPath;
    }

    protected String getLogFileName() {
        return this._logfilename;
    }

    protected void setLogFileName(String logFileName) {
        this._logfilename = logFileName;
    }

    protected Level getLoggerLevel() {
        return _loggerlevel;
    }

    protected void setLoggerLevel(Level loggerLevel) {
        this._loggerlevel = loggerLevel;
    }

    protected String getLoggerPatternLayout() {
        return _loggerlayout;
    }

    protected void setLoggerPatternLayout(String loggerPattern) {
        this._loggerlayout = loggerPattern;
    }


    public static void sleepms(long milliseconds) {
        //Sleeper.sleepTight(milliseconds);
        long end_time = System.currentTimeMillis() + milliseconds;
        while (System.currentTimeMillis() < end_time) {}
    }

    public static void sleep(int seconds) {
        sleepms(seconds*1000);
    }

    public static void log(String message) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Reporter.log(dateFormat.format(new Date()) + ": " + message);// + "<br>");
        logger.info(message);
    }
}
