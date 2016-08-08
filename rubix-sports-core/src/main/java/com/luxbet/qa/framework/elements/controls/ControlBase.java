package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.core.PageBase;
import com.luxbet.qa.framework.elements.icontrols.IControlBase;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public abstract class ControlBase extends PageBase implements IControlBase {
	private static Logger logger = Logger.getLogger(ControlBase.class);
	private WebElement _element;
	@SuppressWarnings("unused")
	private By _by;
	
	public ControlBase(WebElement element) {
		this._element = element;
	}
	
	public ControlBase(By by) {
		this(getDriver().findElement(by));
		this._by = by;
	}
	
	public void VerifyEnabled() {
		logger.info("verifyEnabled()");
		Verify.verifyElementIsEnabled(_element);
	}
	
	public void VerifyDisabled() {
		logger.info("verifyDisabled()");
		Verify.verifyElementIsDisabled(_element);		
	}
	
	public void VerifyDisplayed() {
		logger.info("verifyDisplayed()");
		Verify.verifyElementIsDisplayed(_element);
	}
	
	public void VerifyNotDisplayed() {
		logger.info("verifyNotDisplayed()");
		Verify.verifyElementIsNotDisplayed(_element);
	}
	
	public void VerifyTooltip(String tooltip) {
		// Title works for most of the elements but some exceptions might be required (e.g. Alt for image)
        String _attr = "title";
        VerifyAttribute(_attr, tooltip);
	}

    public void VerifyFocused()
    {
        WebElement focusedElement = getDriver().switchTo().activeElement();

        Verify.verifyEquals(focusedElement, _element, "VerifyFocused() => Web Element not focused <" + _element + ">");
        
//        if (focusedElement == null) {
//            String elementTxt = getText(_element);
//            String focusedElementTxt = getText(focusedElement);
//            Verify.verifyTrue(elementTxt.equals(focusedElementTxt), "VerifyFocused() => Web Element " + elementTxt + " was not focused"); 
//        }      
    }

	private void VerifyAttribute(String attribute, String expectedValue) {
		String attrValue = _element.getAttribute(attribute);
 
        Verify.verifyTrue(attrValue.equals(expectedValue), "Unexpected attribute value. Attribute = '" + attribute + "', Actual <" + _element.getAttribute(attribute) + ">, Expected <" + expectedValue + ">.");
    }

}
