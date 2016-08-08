package com.luxbet.qa.framework.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


/**
 * <b>Description: 
 * All Page Object classes must be extended from this base class
 * @author Selva Nakuladeva
 * @since 21 July 2014
 */
public abstract class PageBase extends SeleniumBase {
	private static Logger logger = Logger.getLogger(PageBase.class);
		
	public static void waitForIframeAndSwitchToIt(String iframeID) {
		logger.info(String.format("waitForIframeAndSwitchToIt(%s)", iframeID));
		try {
			getDriver().switchTo().defaultContent(); 
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS);
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeID));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}	
	
	public boolean waitForClickableElementById(String elementId) {
		logger.info(String.format("waitForPageElementById(%s)", elementId));
		return waitForClickableElementById(elementId, _TIMEOUT_SECONDS);
	}
	
	public boolean waitForClickableElementById(String elementId, int timeoutSeconds) {
		logger.info(String.format("waitForClickableElementById(%s)", elementId));
		return waitForClickableElement(By.id(elementId), timeoutSeconds);
	}
	
	public static boolean waitForClickableElement(By locator) {
		logger.info(String.format("waitForClickableElement(%s)", locator));
		return waitForClickableElement(locator, _TIMEOUT_SECONDS);
	}
	
	public static boolean waitForClickableElement(By locator, int timeoutSeconds) {
		logger.info(String.format("waitForClickableElement(%s)", locator));
		boolean clickable = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds, 200);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
			clickable = true;
			logger.info("The element is clickable >> " + locator);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}	
		return clickable;
	}
	
	public boolean waitForClickableElement(WebElement element) {
		logger.info(String.format("waitForClickableElement(%s)", element));
		return waitForClickableElement(element, _TIMEOUT_SECONDS);
	}
	
	public boolean waitForClickableElement(WebElement element, int timeoutSeconds) {
		logger.info(String.format("waitForClickableElement(%s)", element));
		boolean clickable = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds, 200);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			clickable = true;
			logger.info("The element is clickable >> "+ element);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}	
		return clickable;
	}
	
	public WebElement waitForClickable(WebElement element) {
		logger.info(String.format("waitForClickable(%s)", element));
		WebElement elementToClick = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS, 200);
			elementToClick = wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.info("The element is clickable >> "+ element);
		} catch (Exception e) {
			logger.info(e.getMessage());
		}	
		return elementToClick;
	}
	
	public boolean waitForPageElement(By locator) {
		logger.info(String.format("waitForPageElement(%s)", locator));
		return waitForVisibilityOfElement(locator, _TIMEOUT_SECONDS) != null ? true : false;
	}
		
	public void waitForElementPresent(final By by, int timeoutSeconds){ 
		WebDriverWait wait = (WebDriverWait)new WebDriverWait(getDriver(), timeoutSeconds)
		                  .ignoring(StaleElementReferenceException.class); 
		wait.until(new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver webDriver) {
				WebElement element = webDriver.findElement(by);
				return element != null && element.isDisplayed();
			}
		});
	}
	
	public void waitAndClick(By by) {
		logger.info(String.format("waitAndClick(%s)", by));
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS);
			WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(by));
			clickable.click();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void waitAndClick(WebElement element) {
		logger.info(String.format("waitAndClick(%s)", element));
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS);
			WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(element));
			clickable.click();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public void clearAndInput(By locator, String text) {
		logger.info(String.format("clearAndInput(%s)", text));

		clearAndInput(getDriver().findElement(locator), text);
	}
	
	public void clearAndInput(WebElement element, String text) {
		logger.info(String.format("clearAndInput(%s, %s)", element, text));

		try {
			element.clear();
            sleepms(100);
			element.sendKeys(text + "\t");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	/**
	 * This method clears and inputs a cell and leaves the control in the same cell
	 * @param element
	 * @param text
	 */
	public void clearAndInputStayInCell(WebElement element, String text) {
		logger.info(String.format("clearAndInput(%s, %s)", element, text));

		try {
			element.clear();
			sleepms(100);
			element.sendKeys(text);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}
		
	protected Select getSelectOptions(WebElement element) {
		logger.info(String.format("getSelectOptions(%s)", element));
		Select options = null;
	    try {
	    	options = new Select(element);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	    return options;
	}
	
	public void hover(WebElement element)
    {
		sleepms(200);
        Actions action = new Actions(getDriver());
        //move to the element to hover
        action.moveToElement(element).build().perform();
        sleepms(500);
    }
	
	public void doubleClick(WebElement element)
    {
		Actions action = new Actions(getDriver());
        action.doubleClick(element).build().perform();
        sleepms(200);
    }

	// Added by Dhruba on 03/03/2016
	protected void performSendKeysAndEnterAction(WebElement cell, String valueToEnter) {
		doubleClick(cell);
		Actions action = new Actions(getDriver());
		action.sendKeys(valueToEnter);
		action.sendKeys(Keys.RETURN);
		action.build().perform();
	}

	// Added by Dhruba on 03/03/2016
	protected void performClickAction(WebElement cell) {
		Actions actions = new Actions(getDriver());
		actions.moveToElement(cell);
		actions.click();
		actions.build().perform();
	}

	// Added by Dhruba on 16/03/2016
	protected void SendKeysF1Action() {
		Actions actions = new Actions(getDriver());
		actions.sendKeys(Keys.F1);
		actions.build().perform();
	}

	public void contextMenuClick(WebElement performRightClickOn) {
		Actions action = new Actions(getDriver());
//		action.moveToElement(performRightClickOn).build();
//		action.contextClick(performRightClickOn).build();  /* this will perform right click */
		action.moveToElement(performRightClickOn).contextClick(performRightClickOn).build().perform();
	}
	
	public void dragAndDrop(WebElement elementToMove, WebElement moveToElement) {
		Actions builder = new Actions(getDriver());

		// drag and drop
		Action dragAndDrop = builder.clickAndHold(elementToMove)
		   .moveToElement(moveToElement)
		   .release(moveToElement)
		   .build();

		dragAndDrop.perform();
	}

	public void moveToElement(WebElement element) {
		new Actions(getDriver()).moveToElement(element).perform();
	}
	
	protected void buildAndExecuteActions(Actions builder) {
		// get the action:
		Action actions = builder.build();
		// and execute it:
		actions.perform();
	}
	
	public static WebElement waitForPresenceOfElement(By locator) {
		logger.info(String.format("waitForElement(%s)", locator));
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			//element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return element;
	}
	
	public static WebElement waitForPresenceOfElement(By locator, int seconds) {
		logger.info(String.format("waitForElement(%s, %s)", locator, seconds));
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  seconds);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			//element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return element;
	}
	
	public Boolean waitTillDisplay(final By locator){
	    WebDriverWait wait = new WebDriverWait(getDriver(), _TIMEOUT_SECONDS); 
	    Boolean displayed = wait.until(new ExpectedCondition<Boolean>() {
	    	public Boolean apply(WebDriver driver) {
	    		return driver.findElement(locator).isDisplayed();      						}
        	});
        return displayed;
	}
	
	public Boolean waitTillEnabled(final By locator){
	    WebDriverWait wait = new WebDriverWait(getDriver(), _TIMEOUT_SECONDS); 
	    Boolean enabled = wait.until(new ExpectedCondition<Boolean>() {
	    	public Boolean apply(WebDriver driver) {
	    		return driver.findElement(locator).isEnabled();      				}
        	});
        return enabled;
	}	
	
	public WebElement waitForVisibilityOfElement(By locator, int timeoutSeconds) {
		logger.info(String.format("waitForVisibilityOfElement(%s)", locator));
		WebElement element = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds);
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return element;
	}
	
	public WebElement waitForVisibilityOf(WebElement element) {
		logger.info(String.format("waitForVisibilityOfElement()"));
		WebElement visibleElement = null;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS);
			visibleElement = wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return visibleElement;
	}
	
	public boolean waitForInvisibilityOf(By locator, int timeoutSeconds) {
		logger.info(String.format("waitUntilInvisibilityOfElement(%s)", locator));
		boolean invisibility = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds);
			invisibility = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}		
		return invisibility;
	}
	
	public boolean waitUntilModalScreenDisappear() {
		return waitForInvisibilityOf(By.className("ModalScreen"), _TIMEOUT_SECONDS);	
	}
	
	public static boolean waitForStalenessOf(WebElement element, int timeoutSeconds) {
		logger.info(String.format("waitForStalenessOf(%s)", element));
		boolean staleness = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  timeoutSeconds);
			staleness = wait.until(ExpectedConditions.stalenessOf(element));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return staleness;
	}
	
	public boolean waitForDialog(String fragmentOfTitle) {
		logger.info(String.format("waitForStalenessOf(%s)", fragmentOfTitle));
		boolean is_displayed = false;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(),  _TIMEOUT_SECONDS);
			is_displayed = wait.until(ExpectedConditions.titleContains(fragmentOfTitle));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return is_displayed;
	}

	/**
	 * This method returns the text from element. If the text is empty, it checks the outerText
	 * @param element
	 * @return
	 */
	public String getText(WebElement element) {
		logger.info(String.format("getText(%s)", element));
		
		String text = element.getText();
		if (text.isEmpty()) {
            text = element.getAttribute("outerText");
        }
		return text;	
	}
	
	@SuppressWarnings("unused")
	private void setValueUsingJS(WebElement element, String value) {
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
        executor.executeScript("arguments[0].value = arguments[1]", element, value);
    }
	
	protected void scrollIntoViewUsingJS(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor)getDriver();
		executor.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	protected WebElement getParent(WebElement child) {
		WebElement parent = child.findElement(By.xpath(".."));
		return parent;
	}
	
	/**
	 * Switches to the element that currently has focus within the page.
	 * @return Currently focused element or the body element if this cannot be detected
	 * @author Selva Nakuladeva
     * @since 04 Aug 2014
	 */
	protected WebElement getFocusedElement() {
		WebElement element = getDriver().switchTo().activeElement();
		return element;
	}

    /**
     * @param e --> Current Web Element
     * @param ancestor --> Ancestor element tagname e.g. div will select all ancestor divs and return first ancestor
     * @param predicate --> used to find a specific node or a node that contains a specific value e.g. @class='builder_input'
     * @return first ancestor element found
     * @author Selva Nakuladeva
     * @since 21 July 2014
     */
    public WebElement getAncestor(WebElement e, String ancestor, String predicate)
    {
        //predicate = (!predicate.isEmpty()) ? predicate : "[" + predicate + "]");
        try
        {
            return e.findElement(By.xpath("ancestor::" + ancestor + predicate));
        }
        catch (NoSuchElementException ex)
        {
            return null;
        }
    }

	public WebElement getMatchingElement(List<WebElement> elements, String nameToMatch) {
		WebElement matchingElement = null;
		for(WebElement element : elements) {
			if(getText(element).equals(nameToMatch)) {
				matchingElement = element;
				break;
			}
		}
		return matchingElement;
	}
}
