package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.ILabel;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Label extends ControlBase implements ILabel {
	private static Logger logger = Logger.getLogger(Label.class);
	private WebElement _label;
	@SuppressWarnings("unused")
	private By _by;

	public Label(WebElement label) {
		super(label);
		this._label = label;
	}

	public Label(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}

	public Label(String labelId) {
		this(By.id(labelId));
	}

    @Override
    public String Get() {
        logger.info("Get() called...");
        return getText(_label);
    }

    @Override
    public void VerifyLabel(String expected) {
        logger.info(String.format("VerifyLabel(%s)", expected));
		Verify.verifyEquals(Get(), expected, "Verification failure: Mismatch in label >>, Actual <" + Get() + ">, Expected <" + expected + ">.");
    }

    @Override
    public void VerifyLabelContains(String partialLabel) {
        logger.info(String.format("VerifyLabelContains(%s)", partialLabel));
		Verify.verifyTrue(Get().contains(partialLabel), "Verification failure: Label does not contain '" + partialLabel + "', Actual <" + Get() + ">.");
    }
}
