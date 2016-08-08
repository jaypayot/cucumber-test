package com.luxbet.qa.framework.elements.controls;

import com.luxbet.qa.framework.elements.icontrols.ITextBox;
import com.luxbet.qa.framework.testng.Verify;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class TextBox extends ControlBase implements ITextBox{
	private static Logger logger = Logger.getLogger(TextBox.class);
	
	private WebElement _textbox;
	@SuppressWarnings("unused")
	private String _id;

	public TextBox(WebElement textbox) {
		super(textbox);
		this._textbox = waitForVisibilityOf(textbox);
		this._id = waitForVisibilityOf(textbox).getAttribute("id");
	}
	
	public TextBox(By by) {
		this(waitForPresenceOfElement(by));
	}

	public TextBox(String textBoxId) {
		this(By.id(textBoxId));
	}

	@Override
	public void Set(String text) {
		logger.info(String.format("Set(%s)", text));		
		clearAndInput(_textbox, text);
		logger.info("Textbox value is set to '" + text + "'");
	}

	@Override
	public void Clear() {
		logger.info("Clear()" + _textbox);	
		clearAndInput(_textbox, "");
		logger.info("Textbox value is cleared.");
	}

	@Override
	public String Get() {
		String text;
        if(_textbox.getAttribute("type").equals("password")) {
            text = _textbox.getText();
        } 
        else {
        	text = _textbox.getAttribute("value");
        }
        return text;
	}

	@Override
	public void VerifyText(String expected) {
		Verify.verifyText(Get(), expected, "Verification failure: Expected<" + expected +">, Actual<" + Get() + ">");
	}

	@Override
	public void VerifyEmpty() {
		Verify.verifyFalse(Get().length() > 0, "Verification failure: Expected <Empty>, Actual<Not Empty>");
	}

	@Override
	public void VerifyNotEmpty() {
		Verify.verifyTrue(Get().length() > 0, "Verification failure: Expected <Not Empty>, Actual<Empty>");
	}

	@Override
	public void VerifyFieldLength(String textToEnter, int expectedLength) {
		//String expected = textToEnter.substring(0, expectedLength);
		Set(textToEnter);
		Verify.verifyTrue((Get().length() == expectedLength), "Field length validation failed, Expected length <" + expectedLength + ">, Actual <" + Get().length() + "> Entered Text: " + Get());
	}
}
