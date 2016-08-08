package com.luxbet.qa.framework.core;

import com.luxbet.qa.framework.utils.Database;
import org.apache.log4j.Logger;
import org.testng.ITestContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;

/**
 * <b>Description: 
 * This is the base class for all test cases. All test cases must be extended from this base class.
 * @author Selva Nakuladeva
 * @since 21 July 2014
 */
public abstract class TestBase extends SeleniumBase {
	private static Logger logger = Logger.getLogger(TestBase.class);
	
	private static boolean log4jInitialised = false;
	private static boolean browserOpen = false;
	private static boolean dbInitialised = false;
	protected static Database db;

	protected ITestContext myTestContext;

	/**
	 * Description:
	 * Call it to initialise Log4j and driver at the beginning of the test
	 * @author Selva Nakuladeva
	 * @since 23 July 2014
	 */
	protected void initialiseTest(String table, String recordRowNumber){
		if (!log4jInitialised) {
			initialiseLog4jLogger();
		}
		if (!dbInitialised) {
			db = new Database();
			dbInitialised = true;
		}

		if (!isDriverInitialised()) {
			try {
				initialiseDriver();
			} catch (Exception e) {
				logger.error("Exception occured while initialising driver!", e);
				e.printStackTrace();
			}
		}
		// Load AUT ready for testing - url specified in the test record
		launchApp();
		logger.info("Test initialised.");
	}

	
	/**
	 * Description: Call this method at the beginning of any test to initialise 
	 * data table and row number.
	 * @author Selva Nakuladeva
	 * @since 23 July 2014
	 */
	private void initialiseTestData(String table, String recordRowNumber) {
		logger.info(String.format("initialiseDatabase(%s)", table, recordRowNumber));
		if (!dbInitialised) {
			db = new Database();
		}
		db.setTable(table);
		db.setRecordNumber(recordRowNumber);	
	}
	
	/**
	 * Description: Must be called to finalise and quit the driver 
	 * @author Selva Nakuladeva
	 * @since 24 July 2014
	 */
	public void finaliseTest() {
		logger.info("-> FinaliseTest() <Started>");
    	// Set suite mode to false
        setSuiteMode(false);	
		//The ChromeDriver *requires* that you call close and then quit to not leak resources. 
		//However, the firefoxdriver crashes if you call close before calling quit with any time delay. 
        if (isDriverInitialised())
        {
        	logger.info("-> FinaliseTest() Quitting Driver...");
            finaliseDriver();
        }
        logger.info("-> FinaliseTest() Closing Database connection...");
		closeDatabaseConnection();
		logger.info("Database connection closed.");	
		
		logger.info("-> FinaliseTest() <Completed>");
	}
	
	
	private void closeDatabaseConnection() {
		logger.info("closeDatabaseConnection()");
		if (dbInitialised) {
			db.closeConnection();
		}
		dbInitialised = false;
	}
	
	public void launchApp() {
		logger.info(String.format("launchApp() called..."));
		navigateTo(getBaseUrl());
		maximize();
		browserOpen = true;
		log("Application loaded successfully.");	
	}
	
	/**
	 * Description: Closes the browser instance and quits the driver 
	 * @author Selva Nakuladeva
	 * @since 21 July 2014
	 */
	public void closebrowser() {
		getDriver().quit();
		browserOpen = false;
		logger.info("Browser closed.");		
	}
		
	/**
	 * Description: Kills specified running Windows process e.g. chromedriver.exe
	 * @return true if process is killed, false if not
	 * @author Selva Nakuladeva
	 * @since 24 July 2014
	 */
    public static boolean killProcess(String serviceName) throws Exception {
		Process p = Runtime.getRuntime().exec("tasklist");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    String line;
	    boolean found = false;
	    
	    while ((line = reader.readLine()) != null) {
	    	logger.info(line);
	    	if (line.contains(serviceName)) {
	    		found = true;
	    		logger.info("Killing Process: " + serviceName);
	    		Runtime.getRuntime().exec("taskkill /F /IM " + serviceName);
	    	}
	    }
	    return found;
    }	
    
    public static void createScreenshot() {
//    	ITestResult result = Reporter.getCurrentTestResult();
//    	String imagePath="-screenshot-" + (new SimpleDateFormat("dd_MMM_yyyy__hh_mm_ss_Saa").format(new Date())) + ".png";
//    	try {
//    		if (!result.isSuccess() && getDriver() instanceof TakesScreenshot) {
//    	        TakesScreenshot screenShotter = (TakesScreenshot) (getDriver());
//    	        //File target = new File("target" + File.separator + "screenshots" + File.separator + result.getTestClass().getName().replace(".", File.separator) + result.getMethod().getMethodName() + ".png");
//    	        //File target = new File("surefire-reports" +File.separator+ "html" +File.separator+ "screenshots" +File.separator+ result.getTestClass().getName().replace(".", File.separator) + imagePath);
//    	        File target = new File("surefire-reports" +File.separator+ "html" +File.separator+ imagePath);
//    	        FileUtils.copyFile(screenShotter.getScreenshotAs(OutputType.FILE), target);
//    	        logger.info("Stored screenshot in file > " + target);
//				reportLogScreenshot(target);
//				result.setStatus(ITestResult.FAILURE);
//    	    }
//    	}
//		catch (Exception e) {
//    		e.printStackTrace();
//    	}
    }
    
    
    protected static void reportLogScreenshot(File file) {
		//System.setProperty("org.uncommons.reportng.escape-output", "false");
    	String absolute = file.getAbsolutePath();
    	String screenShot = absolute.replace('\\','/');
    
    	log("<a href=\"" + screenShot + "\"><p align=\"left\">Error screenshot at " + new Date()+ "</p>");
    	log("<p><img width=\"1024\" src=\"" + file.getAbsoluteFile()  + "\" alt=\"screenshot at " + new Date()+ "\"/></p></a><br />"); 
   }

	// Overridden in the child class
	public String getBaseUrl() {
		return "";
	}
}

