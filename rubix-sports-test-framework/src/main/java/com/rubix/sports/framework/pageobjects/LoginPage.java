package com.rubix.sports.framework.pageobjects;

import com.luxbet.qa.framework.clcm.rubix_controls.ITextBox_Password;
import com.luxbet.qa.framework.clcm.rubix_controls.TextBox_Password;
import com.luxbet.qa.framework.core.PageBase;
import com.luxbet.qa.framework.elements.controls.Button;
import com.luxbet.qa.framework.elements.controls.TextBox;
import com.luxbet.qa.framework.elements.icontrols.IButton;
import com.luxbet.qa.framework.elements.icontrols.ILink;
import com.luxbet.qa.framework.elements.icontrols.ITextBox;
import com.luxbet.qa.framework.testng.CustomAssert;
import com.rubix.sports.framework.ipageobjects.ILoginPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

/**
 * Created by payotj on 13/07/2016.
 */
public class LoginPage extends PageBase implements ILoginPage {

    private static Logger logger = Logger.getLogger(LoginPage.class);
    // This locator is used to wait for the page to load.
    By _pageWaitLocator = By.cssSelector("div#loginForm button[type=submit]");

    @FindBy(css = "div#loginForm input[placeholder=Username]") @CacheLookup       private WebElement _username;
    @FindBy(css = "div#loginForm input[placeholder=Password]") @CacheLookup       private WebElement _password;
    @FindBy(css = "div#loginForm button[type=submit]") @CacheLookup       private WebElement _login;

    public LoginPage() {
        this.waitForPageToLoad();
        ElementLocatorFactory finder =  new AjaxElementLocatorFactory(getDriver(), _TIMEOUT_SECONDS);
        // This call initialises the WebElement fields
        PageFactory.initElements(finder, this);
        logger.info("Login page elements initialised");
    }

    /**
     * Waits for the page to load. Assertion will fail if the page fails to load within the default timeout period.
     */
    private void waitForPageToLoad() {
        waitForVisibilityOfElement(_pageWaitLocator, 2*_TIMEOUT_SECONDS);
        CustomAssert.assertTrue(waitForClickableElement(_pageWaitLocator), "Assertion! Login page failed to load within the timeout period.");
        logger.info("Login page loaded successfully.");
    }

    public ITextBox Username() {
        return new TextBox(_username);
    }

    public ITextBox_Password Password() {
        return new TextBox_Password(_password);
    }

    @Override
    public ILink ForgotYourPassword() {
        return null;
    }

    @Override
    public void VerifyErrorMessage(String s) {

    }

    public IButton LoginButton() {
        return new Button(_login);
    }
}
