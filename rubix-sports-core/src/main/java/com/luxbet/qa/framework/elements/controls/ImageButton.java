package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.IImageButton;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ImageButton extends ControlBase implements IImageButton {
	private static Logger logger = Logger.getLogger(ImageButton.class);
	private WebElement _imgbutton;
	@SuppressWarnings("unused")
	private By _by;
	
	public ImageButton(WebElement imgbutton) {
		super(imgbutton);	
		this._imgbutton = imgbutton;
	}
	
	public ImageButton(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public ImageButton(String imgbuttonId) {
		this(By.id(imgbuttonId));		
	}

    private boolean isEnabled() {
        boolean enabledState = true;
        String className = this._imgbutton.getAttribute("className");
        if(className.contains("disabled")) {
            enabledState = false;
        }
        return enabledState;
    }

	@Override
	public void Click() {
        //_imgbutton.sendKeys("");
        waitForClickable(_imgbutton).click();
		logger.info("Click() action performed on the image button");
    }

	@Override
	public String GetName() {
		String name = _imgbutton.getAttribute("value").toString().trim();
		logger.info("GetName() returned the image button name: '" + name + "'");		
		return name;
	}

	@Override
	public void VerifyName(String expected) {
		logger.info(String.format("VerifyName(%s)", expected));
		Verify.verifyEquals(GetName(), expected, "Verification failure: Image button name, Actual <" + GetName() + ">, Expected <" + expected + ">.");
	}

    @Override
    public void VerifyEnabled() {
        logger.info("verifyEnabled()");
        Verify.verifyTrue(isEnabled(), "FAIL: Button incorrectly disabled! >> " + this._imgbutton);
    }

    @Override
    public void VerifyDisabled() {
        logger.info("verifyDisabled()");
        Verify.verifyFalse(isEnabled(), "FAIL: Button incorrectly enabled! >> " + this._imgbutton);
    }

    //TODO: Need to check for visibility of the element. Currently this is not working.
    @Override
    public void VerifyNotDisplayed() {
        logger.info("verifyNotDisplayed()");
        TurnOffImplicitWait();
     //   WebElement e = waitForVisibilityOf(_imgbutton);
        boolean b = waitForClickableElement(_imgbutton, 1);
       // Verify.verifyTrue(e==null,"FAIL! Image button is visible incorrectly.");
        Verify.verifyFalse(b,"FAIL! Image button is visible incorrectly.");
        TurnOnImplicitWait();
    }
}
