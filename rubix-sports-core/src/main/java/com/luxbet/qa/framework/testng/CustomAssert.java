package com.luxbet.qa.framework.testng;

import com.luxbet.qa.framework.core.SeleniumBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;

/**
 * <b>Description: This class separates the Unit Testing Framework that is being
 * used to build the core framework, hence providing the ability to plug-in any
 * Unit Testing Framework at a later stage, if necessary, without changing the
 * existing Framework implementation. Currently TestNG has been used as the Unit
 * Testing Framework.
 * 
 * @author Selva Nakuladeva
 * @since 15/07/2014
 */
public class CustomAssert extends SeleniumBase {
	
	protected CustomAssert() {
		super();
	}


	private static Logger logger = Logger.getLogger(CustomAssert.class);

	public static void assertTrue(boolean condition) {
		logger.info(String.format("assertTrue(%s)", condition));
		Assert.assertTrue(condition);
	}

	public static void assertTrue(boolean condition, String message) {
		logger.info(String.format("assertTrue(%s)", condition));
		Assert.assertTrue(condition, message);
	}

	public static void assertFalse(boolean condition) {
		logger.info(String.format("assertFalse(%s)", condition));
		Assert.assertFalse(condition);
	}

	public static void assertFalse(boolean condition, String message) {
		logger.info(String.format("assertFalse(%s, %s)", condition, message));
		Assert.assertFalse(condition, message);
	}

	public static void assertEquals(boolean actual, boolean expected,
			String message) {
		logger.info(String.format("assertEquals(%s, %s)", actual, expected));
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertEquals(Object actual, Object expected) {
		logger.info(String.format("assertEquals(%s, %s)", actual, expected));
		Assert.assertEquals(actual, expected);
	}

	public static void assertEquals(Object actual, Object expected,
			String message) {
		//logger.info(String.format("assertEquals(%s, %s)", actual, expected));
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertEquals(Object[] actual, Object[] expected) {
		logger.info(String.format("assertEquals(%s, %s)", actual, expected));
		Assert.assertEquals(actual, expected);
	}

	public static void assertEquals(Object[] actual, Object[] expected,
			String message) {
		logger.info(String.format("assertEquals(%s, %s, %s)", actual, expected,
				message));
		Assert.assertEquals(actual, expected, message);
	}

	public static void assertSame(Object actual, Object expected) {
		logger.info(String.format("assertSame(%s, %s)", actual, expected));
		Assert.assertSame(actual, expected);
	}

	public static void assertSame(Object actual, Object expected, String message) {
		logger.info(String.format("assertSame(%s, %s, %s)", actual, expected,
				message));
		Assert.assertSame(actual, expected, message);
	}

	public static void assertNotSame(Object actual, Object expected) {
		logger.info(String.format("assertNotSame(%s, %s)", actual, expected));
		Assert.assertNotSame(actual, expected);
	}

	public static void assertNotSame(Object actual, Object expected,
			String message) {
		logger.info(String.format("assertNotSame(%s, %s, %s)", actual,
				expected, message));
		Assert.assertNotSame(actual, expected, message);
	}

	public static void fail(String message) {
		logger.info(String.format("fail(%s)", message));
		Assert.fail(message);
	}

	public static void assertPageLoaded(String expectedPagetitle) {
		logger.info(String.format("assertPageLoaded(%s)", expectedPagetitle));
		assertEquals(getDriver().getTitle(), expectedPagetitle, "'"
				+ expectedPagetitle + "' page failed to load!");
	}

	public static void assertDialogIsDisplayed(By by) {
		logger.info(String.format("assertDialogIsDisplayed(%s)", by));
		assertElementIsDisplayed(by);
	}

	/**
	 * Search text in page source, throws assertion error if page source doesn't
	 * contain the text
	 * 
	 * @author Selva Nakuladeva
	 * @since 17 July 2014
	 */
	public static void assertTextIsPresentOnPage(String text) {
		logger.info(String.format("assertTextIsPresentOnPage(%s)", text));
		if (!getDriver().getPageSource().contains(text)) {
			logger.error("Text '" + text + "' not in page source.");
			throw new AssertionError("Text '" + text + "' not in page source.");
		}
	}

	/**
	 * Search in current page URL, throws assertion error if not found
	 * 
	 * @author Selva Nakuladeva
	 * @since 17 July 2014
	 */
	public static void assertThatURLcontains(String URLfragment) {
		logger.info(String.format("assertThatURLcontains(%s)", URLfragment));
		String url = getDriver().getCurrentUrl();
		assertTrue(url.contains(URLfragment),
				"FAILURE: Current page URL does not contain '" + URLfragment
						+ "'; Actual URL: " + url);
	}

	public static void assertElementIsDisplayed(By by) {
		logger.info(String.format("assertElementIsDisplayed(%s)", by));
		assertElementIsDisplayed(getDriver().findElement(by));
	}

	public static void assertElementIsDisplayed(WebElement element) {
		logger.info(String.format("assertElementIsDisplayed(%s)", element));
		boolean actual = element.isDisplayed();
		assertTrue(actual, "Element not displayed --> " + element);
	}

	public static void assertElementIsNotDisplayed(By by) {
		logger.info(String.format("assertElementIsNotDisplayed(%s)", by));
		WebElement element = null;
		try {
			SeleniumBase.TurnOffImplicitWait();
			element = getDriver().findElement(by);
			SeleniumBase.TurnOnImplicitWait();
			
			if (element != null) {
				assertElementIsNotDisplayed(element);
			}
		} catch (Exception e) {
			assertEquals(element, null, "FAIL: expected <not displayed>, but was <displayed> -->"	+ element);
		}
	}

	public static void assertElementIsNotDisplayed(WebElement element) {
		logger.info(String.format("assertElementIsNotDisplayed(element)"));
		assertFalse(element.isDisplayed(),
				"FAILURE: expected:<not displayed>, but was:<displayed> --> "
						+ element);
	}

	public static void assertElementIsEnabled(By by) {
		logger.info(String.format("assertElementIsEnabled(%s)", by));
		assertElementIsDisplayed(getDriver().findElement(by));
	}

	public static void assertElementIsEnabled(WebElement element) {
		boolean status = element.isEnabled();
		assertTrue(status, "expected:<true: enabled>, actual:<" + status
				+ ": disabled > --> " + element);
	}

	public static void assertElementIsDisabled(By by) {
		logger.info(String.format("assertElementIsDisabled(%s)", by));
		assertElementIsDisabled(getDriver().findElement(by));
	}

	public static void assertElementIsDisabled(WebElement element) {
		boolean actual = element.isEnabled();
		// assertFalse(status,
		// "FAILURE: expected:<disabled>, but was:<enabled> --> " + element);
		assertFalse(actual,
				"FAILURE: expected:<disabled>, but was:<enabled> --> "
						+ element);
	}

	public static void assertElementIsPresent(By by) {
		logger.info(String.format("assertElementIsPresent(%s)", by));
		assertTrue(isElementPresent(by));
	}

	public static void assertText(String actual, String expected, String message) {
		logger.info(String.format("assertText(%s)", expected));
		assertEquals(actual, expected, message);
	}

	public static void assertPageContainsText(String textToFind) {
		logger.info(String.format("assertPageContainsText(%s)", textToFind));
		Assert.assertTrue(getDriver().findElement(By.tagName("body")).getText()
				.contains(textToFind),
				"FAILURE: Page doesn't contain the text => " + textToFind);
	}
	
	
	public static boolean isElementPresent(By by) {
	    try {
	         int count = getDriver().findElements(by).size();
	         if(count == 0){
	    	   return false;
	         }
	         else
	         {
	        	  List<WebElement> items = getDriver().findElements(by);
	        	  WebElement element = items.get(0);
	        	  if (element.isDisplayed()){
		   	    	  System.out.println("Element is Displayed =>"+by);
	        		  return true;
	        	  }
	        	  else
	        	  {
		   	    	  System.out.println("Element is NOT Displayed =>"+by);
	        		  return false;
	        	  }
	          }  
	    } 
	    catch (NoSuchElementException e) {
	    	 return false;
	    }
	}

}
