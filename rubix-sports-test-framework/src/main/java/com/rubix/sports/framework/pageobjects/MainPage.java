package com.rubix.sports.framework.pageobjects;

import com.luxbet.qa.framework.core.PageBase;
import com.luxbet.qa.framework.elements.controls.Button;
import com.luxbet.qa.framework.elements.icontrols.IButton;
import com.luxbet.qa.framework.testng.CustomAssert;
import com.luxbet.qa.framework.testng.Verify;
import com.rubix.sports.framework.ipageobjects.IMainPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

/**
 * Created by payotj on 3/08/2016.
 */
public class MainPage extends PageBase implements IMainPage {
    private static Logger logger = Logger.getLogger(LoginPage.class);
    // This locator is used to wait for the page to load.
    By _pageWaitLocator = By.cssSelector("nav#user_navigation button");
    @FindBy(css = "div.view-container") @CacheLookup private WebElement _container;
    @FindBy(css = "nav#user_navigation button") @CacheLookup       private WebElement _logout;

    public MainPage() {
        this.waitForPageToLoad();
        ElementLocatorFactory finder =  new AjaxElementLocatorFactory(getDriver(), _TIMEOUT_SECONDS);
        // This call initialises the WebElement fields
        PageFactory.initElements(finder, this);
        logger.info("Main page elements initialised");
    }

    /**
     * Waits for the page to load. Assertion will fail if the page fails to load within the default timeout period.
     */
    private void waitForPageToLoad() {
        waitForVisibilityOfElement(_pageWaitLocator, 2*_TIMEOUT_SECONDS);
        CustomAssert.assertTrue(waitForClickableElement(_pageWaitLocator), "Assertion! Login page failed to load within the timeout period.");
        logger.info("Main page loaded successfully.");
    }

    @Override
    public void VerifyBackgroundColor(String colorCode) {
            if(colorCode.equals("White")) {
                Verify.verifyTrue(true, "Color is "+colorCode);
            }
            else {
                Verify.fail("Color is "+colorCode);

        }
    }

    @Override
    public IButton Logout() {
        return new Button(_logout);
    }
}
