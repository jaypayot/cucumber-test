package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.IButton;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Button extends ControlBase implements IButton {
	private static Logger logger = Logger.getLogger(Button.class);
	private WebElement _button;
	@SuppressWarnings("unused")
	private By _by;
	
	public Button(WebElement button) {
		super(button);	
		this._button = button;
	}
	
	public Button(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public Button(String buttonId) {
		this(By.id(buttonId));
	}

	@Override
	public void Click() {
        waitAndClick(_button);
		logger.info("Click() action performed on the button");
    }

	@Override
	public String GetName() {
		String name = _button.getAttribute("value").toString().trim();
		logger.info("GetName() returned the button name: '" + name + "'");		
		return name;
	}

	@Override
	public void VerifyName(String expected) {
		logger.info(String.format("VerifyName(%s)", expected));
		Verify.verifyEquals(GetName(), expected, "Verification failure: Button name, Actual <" + GetName() + ">, Expected <" + expected + ">.");
	}

}
