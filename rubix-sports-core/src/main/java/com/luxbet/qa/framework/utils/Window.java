package com.luxbet.qa.framework.utils;


import com.luxbet.qa.framework.core.PageBase;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

/**
 * <b>Description:
 * Facilitates dealing with popup messages and new windows. Keeps track of the current active window when switching to a new window.
 * @author Selva Nakuladeva
 * @since 4/12/2014.
 */
public class Window extends PageBase {
    private static Logger logger = Logger.getLogger(Window.class);

    private static WebDriver _driver = getDriver();
    private static String _currentWindowHandle = getDriver().getWindowHandle();
    private static boolean switchedToNewWindow = false;

//    public Window(WebDriver driver) {
//        this._driver = driver;
//        this._currentWindowHandle = driver.getWindowHandle();
//    }

    public static String getNewWindowHandle(){
        logger.info("getNewWindowHandle() >>");

        java.util.Set<String> handles = _driver.getWindowHandles();

        handles.remove(_currentWindowHandle);

        if (handles.size() > 0) {
            return handles.iterator().next();
        }
        else {
            logger.warn("Did not find new window");
            return null;
        }
    }

    public static boolean isNewWindowOpen() {
        logger.info("isNewWindowOpen() >>");
        log("isNewWindowOpen()",true);
        try {
            java.util.Set<String> handles = _driver.getWindowHandles();

            handles.remove(_currentWindowHandle);

            if (handles.size() > 0) {
                logger.info("New window is present");
                return true;
            } else {
                logger.error("Did not find new window");
                return false;
            }
        }
        catch(Exception e) {
            logger.error(e.getMessage());
            log("Exception when trying to check if new window is open",true);
            return false;
        }
    }

    public static WebDriver switchToNewWindow(){
        logger.info("switchToNewWindow()");
        try {
        java.util.Set<String> handles = _driver.getWindowHandles();

        handles.remove(_currentWindowHandle);

            if (handles.size() > 0) {

                _driver = _driver.switchTo().window(handles.iterator().next());
                logger.info("Switched to new window titled: " + _driver.getTitle());
                logger.info("Current Url: " + _driver.getCurrentUrl());
                switchedToNewWindow = true;

                return _driver;
                }
            }
        catch (Exception e) {
            logger.error("Exception while switching to new window"+e.getMessage());
            switchedToNewWindow = false;
            return null;
        }
        logger.info("Did not find window");
        return null;
    }

    public static void closeNewWindow() {
        logger.info("closeNewWindow()");

        if (switchedToNewWindow) {
            _driver.close();
            _driver.switchTo().window(_currentWindowHandle);
        }
        else {
            switchToNewWindow().close();
            _driver.switchTo().window(_currentWindowHandle);
        }
    }

    public static void setSize(Dimension dimension) {
        _driver.manage().window().setSize(dimension);
    }
}
