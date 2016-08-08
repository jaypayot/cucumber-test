package com.rubix.sports.framework.webdriver;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

/**
 * Created by payotj on 20/07/2016.
 */
//@Component
public class SharedDriver extends EventFiringWebDriver{
    private static WebDriver REAL_DRIVER;
    private static final Thread CLOSE_THREAD = new Thread() {

    @Override
    public void run() {
        quitGlobalInstance();
    }
    };

private static void quitGlobalInstance() {
        WebDriver driver = REAL_DRIVER;
        REAL_DRIVER = null;
        if (driver != null) {
        driver.quit();
        }
        }

    private static WebDriver getRealDriver() {
        if (REAL_DRIVER == null) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("--disable-extensions");
            DesiredCapabilities dc = DesiredCapabilities.chrome();
            dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
            dc.setCapability(ChromeOptions.CAPABILITY, options);
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\Drivers\\chromedriver.exe");
            REAL_DRIVER = new ChromeDriver(options);
        }
        return REAL_DRIVER;
    }

    public SharedDriver() {
        super(getRealDriver());
//        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }
    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        try {
            super.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
