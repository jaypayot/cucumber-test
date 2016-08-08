package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.ILink;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Link extends ControlBase implements ILink {
	private static Logger logger = Logger.getLogger(Link.class);
	private WebElement _link;
	@SuppressWarnings("unused")
	private By _by;
	
	public Link(WebElement link) {
		super(link);	
		this._link = link;
	}
	
	public Link(By by) {
		this(waitForPresenceOfElement(by));
		this._by = by;
	}
	
	public Link(String linkId) {
		this(By.id(linkId));
	}

	@Override
	public void Click() {
        //_link.sendKeys("");
        _link.click();
		logger.info("Click() action performed on the link");
    }

	@Override
	public String GetLinkText() {
		String linkText = _link.getText();
		logger.info("GetText() action performed on the link");
		return linkText;
	}

	@Override
	public void VerifyName(String expected) {
		logger.info(String.format("VerifyName(%s)", expected));
		Verify.verifyEquals(getText(_link), expected, "Verification failure: Link name, Actual <" + getText(_link) + ">, Expected <" + expected + ">.");
	}

	@Override
	public void VerifyLinkContains(String partialLink) {
		logger.info(String.format("VerifyLinkContains(%s)", partialLink));
		Verify.verifyTrue(getText(_link).contains(partialLink), "Verification failure: Link does not contain '" + partialLink + "', Actual <" + getText(_link) + ">.");
	}

}
