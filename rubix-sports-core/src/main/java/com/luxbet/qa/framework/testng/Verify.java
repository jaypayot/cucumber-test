package com.luxbet.qa.framework.testng;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * <b>Description: 
 * This class separates the Unit Testing Framework that is being used to build the core framework, hence providing the ability
 * to plug-in any Unit Testing Framework at a later stage, if necessary, without changing the existing Framework implementation. 
 * Currently TestNG has been used as the Unit Testing Framework.
 * @author Selva Nakuladeva
 * @since 15/07/2014
 */
public class Verify extends CustomAssert {
	private static Logger logger = Logger.getLogger(Verify.class);

	/** TestNG error collector */
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
		
	private static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}
		   
    public static void verifyTrue(boolean condition) {
    	logger.info(String.format("verifyTrue(%s)", condition));
    	try {
    		CustomAssert.assertTrue(condition);
    		log("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyTrue condition failed", true);
    	}
    }
    
    public static void verifyTrue(boolean condition, String message) {
    	logger.info(String.format("verifyTrue(%s)", condition));
    	try {
    		CustomAssert.assertTrue(condition, message);
    		log("PASS: VerifyTrue condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyTrue condition failed >> " + message, true);
    	}
    }

	public static void verifyTrue(boolean condition, String message, boolean takeScreenshot) {
		//logger.info(String.format("verifyTrue(%s)", condition));
		try {
			CustomAssert.assertTrue(condition, message);
			log("PASS: VerifyTrue condition is met");
		} catch(Throwable t) {
			//logger.error(t.getMessage());
			addVerificationFailure(t);
            if (takeScreenshot) {
                log("FAIL: VerifyTrue condition failed >> " + message, true);
            } else {
                log("FAIL: VerifyTrue condition failed >> " + message);
            }
		}
	}
    
    public static void verifyFalse(boolean condition) {
    	logger.info(String.format("verifyFalse(%s)", condition));
    	try {
    		CustomAssert.assertFalse(condition);
    		log("PASS: VerifyFalse condition is met");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyFalse condition failed", true);
		}
    }
    
    public static void verifyFalse(boolean condition, String message) {
    	logger.info(String.format("verifyFalse(%s)", condition));
    	try {
    		CustomAssert.assertFalse(condition, message);
    		log("PASS: VerifyFalse condition is met");
    	} catch(Throwable t) {
    		logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: VerifyFalse condition failed >> " + message, true);
    	}
    }
    
    public static void verifyEquals(boolean actual, boolean expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'", true);
		}
    }

    public static void verifyEquals(Object actual, Object expected, String message) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected, message);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "' ; Detail message > " + message, true);
		}
    }

	public static void verifyEquals(Object actual, Object expected, String message, boolean takeScreenshot) {
		//logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
		try {
			CustomAssert.assertEquals(actual, expected, message);
			log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			if (takeScreenshot) {
				log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "' ; Detail message > " + message, true);
			} else {
				log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "' ; Detail message > " + message);
			}
		}
	}

	public static void verifyEquals2(Object actual, Object expected, String message) {
		//logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
		try {
			//CustomAssert.assertEquals(actual, expected);
			Assert.assertEquals(actual, expected, message);
			log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			//logger.error(t.getMessage());
			addVerificationFailure(new Throwable(message));
			log("FAIL: Actual value: \n" + actual + "\n does not match expected value: \n" + expected + "\n");
		}

//		public static <T> void assertList(List<T> actual, List<T> expected) {
//
//			assertEquals(join(expected, "\n"), join(actual, "\n"));
//
//		}
	}

    public static void verifyEquals(Object[] actual, Object[] expected) {
    	logger.info(String.format("verifyEquals(%s, %s)", actual, expected));
    	try {
    		CustomAssert.assertEquals(actual, expected);
    		log("PASS: Actual value '" + actual + "' matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Actual value '" + actual + "' does not match expected value '" + expected + "'", true);
		}
    }

	public static void verifyPageLoaded(String expectedPagetitle) {
		logger.info(String.format("verifyPageLoaded(%s)", expectedPagetitle));			
		try {
			CustomAssert.assertPageLoaded(expectedPagetitle);
			log("PASS: '" + expectedPagetitle + "' page loaded successfully");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: '" + expectedPagetitle + "' page failed to load", true);
		}
	}

	public static void verifyDialogIsDisplayed(By by) {
		logger.info(String.format("verifyDialogIsDisplayed(%s)", by));		
		try {
			CustomAssert.assertDialogIsDisplayed(by);	
			log("PASS: Expected dialog is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Failed to find the expected dialog, by >> " + by, true);
		}
	} 
	
	/**
	 * Verifies if the element is present on the page
	 * @author Selva Nakuladeva
	 * @since 17 July 2014
	 */
	public static void verifyElementIsPresent(By by) {
		logger.info(String.format("verifyElementIsPresent(%s)", by));
		try{
			CustomAssert.assertElementIsPresent(by);
			log("PASS: Expected element is present");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Expected element not present, by >> " + by, true);
		}
	}
			 		
	/**
	 * This will verify the specified text is present on the current page source
	 * @author Selva Nakuladeva
	 * @since 17 July 2014
	 */
	public static void verifyTextIsPresentOnPage(String text) {
		logger.info(String.format("verifyTextIsPresentOnPage(%s)", text));
		try {
			CustomAssert.assertTextIsPresentOnPage(text);
			log("PASS: Expected text '" + text + "' is present on the page");
		} catch (Throwable t){
			addVerificationFailure(t);
			log("FAIL: Text '" + text + "' NOT present on the page", true);
		}
	}
	
	/**
	 * Verifies that current page URL contains the URL fragment
	 * @author Selva Nakuladeva
	 * @since 17 July 2014
	 */
	public static void verifyThatURLcontains(String URLfragment) {
		logger.info(String.format("verifyThatURLcontains(%s)", URLfragment));
		try {
			CustomAssert.assertThatURLcontains(URLfragment);
			log("PASS: URL contains the expected URL fragment '" + URLfragment + "'");
		} catch(Throwable t) {
			addVerificationFailure(t);
			log("FAIL: URL does not contain the expected URL fragment '" + URLfragment + "'", true);
		}
	}
		
	public static void verifyElementIsDisplayed(By by) {
		logger.info(String.format("verifyElementIsDisplayed(%s)", by));
		try {
			CustomAssert.assertElementIsDisplayed(by);	
			log("PASS: Expected element is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Expected element not displayed, by >> " + by, true);
		}
	}
	
	public static void verifyElementIsDisplayed(WebElement element) {
		logger.info("--> verifyElementIsDisplayed()");
		try {
			CustomAssert.assertElementIsDisplayed(element);		
			log("PASS: Expected element is displayed");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Expected element not displayed >> " + element, true);
		}
	}	

	public static void verifyElementIsNotDisplayed(By by) {		
		logger.info(String.format("verifyElementIsNotDisplayed(%s)", by));
		try {
			CustomAssert.assertElementIsNotDisplayed(by);	
			log("PASS: Element is not displayed correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Element is displayed incorrectly >> " + by, true);
		}	
	}
	
	public static void verifyElementIsNotDisplayed(WebElement element) {
		logger.info("--> verifyElementIsNotDisplayed()");
		try {
			CustomAssert.assertElementIsNotDisplayed(element);
			log("PASS: Element is not displayed correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is displayed incorrectly >> ", true);
		}
	}
	
	public static void verifyElementIsEnabled(By by) {		
		logger.info(String.format("verifyElementIsEnabled(%s)", by));
		try {
			CustomAssert.assertElementIsEnabled(by);
			log("PASS: Element is enabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is disabled incorrectly >> " + by, true);
		}
	}
	
	public static void verifyElementIsEnabled(WebElement element) {
		logger.info("--> verifyElementIsEnabled(%s)");
		try {
			CustomAssert.assertElementIsEnabled(element);
			log("PASS: Element is enabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);		
			log("FAIL: Element is disabled incorrectly >> " + element, true);
		}
	}

	public static void verifyElementIsDisabled(By by) {
		logger.info(String.format("verifyElementIsDisabled(%s)", by));
		try {
			CustomAssert.assertElementIsDisabled(by);
			log("PASS: Element is disabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is enabled incorrectly >> " + by, true);
		}
	}
	
	public static void verifyElementIsDisabled(WebElement element) {
		logger.info("--> verifyElementIsDisabled()");
		try {
			CustomAssert.assertElementIsDisabled(element);
			log("PASS: Element is disabled correctly");
		} catch (Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Element is enabled incorrectly >> " + element, true);
		}
	}
	
	public static void verifyText(String actual, String expected, String message) {
		logger.info(String.format("verifyText(%s)", expected));			
		try {
			CustomAssert.assertText(actual, expected, message);
			log("PASS: Actual text <" + actual + "> matches expected value");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);	
			log("FAIL: Actual text <" + actual + "> does not match the expected value <" + expected + ">", true);
		}
	}
	
	public static void verifyTextContains(String actual, String partialMatch) {
		logger.info(String.format("verifyTextContains(%s)", partialMatch));			
		try {
			CustomAssert.assertTrue(actual.contains(partialMatch), "Error: No match found for '" + partialMatch + "'; Actual <" + actual + ">");
			log("PASS: Actual value contains '" + partialMatch + "'");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);		
			log("FAIL: Actual value does not contain '" + partialMatch + "'", true);
		}
	}
				
	public static void verifyPageContainsText(String textToFind) {
		logger.info(String.format("verifyPageContainsText(%s)", textToFind));
		try {
			CustomAssert.assertPageContainsText(textToFind);
			log("PASS: Page DOM contains the text '" + textToFind + "'");
		} catch(Throwable t) {
			logger.error(t.getMessage());
			addVerificationFailure(t);
			log("FAIL: Page DOM does not contain the text '" + textToFind + "'", true);
		}
	}
	
}
