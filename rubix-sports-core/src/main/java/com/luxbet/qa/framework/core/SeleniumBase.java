package com.luxbet.qa.framework.core;

import com.luxbet.qa.framework.utils.OSDriverPathHelper;
import org.apache.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * <b>Description: 
 * This is the base class of the Luxbet Automation core framework, defines custom assertion methods using TestNG test framework.
 * PageBase and TestBase are extended from this base class.
 * A static WebDriver instance is stored here and used throughout the framework.
 * @author Selva Nakuladeva
 * @since 15 July 2014
 **/
 public abstract class SeleniumBase extends Base {
	private static Logger logger = Logger.getLogger(SeleniumBase.class);
    private static WebDriver _webDriver = null;
    private static boolean _driverInitialised = false;
    private static String browser;
    private static String _driverPath; //TODO: Use TestNG.XML;
    private static String _driverLocation;
    private String _testName = "";
    private static boolean _suiteMode = false;
	protected final static int _TIMEOUT_SECONDS  = 30;
    //private static String _logpath = null; //TODO: ConfigurationManager.AppSettings["LogPath"];

    public boolean getSuiteMode() { 
    	return SeleniumBase._suiteMode; 
    }
    
    public void setSuiteMode(boolean suitemode) { 
    	SeleniumBase._suiteMode = suitemode; 
    }
    
    public static String getBrowser() {
    	return SeleniumBase.browser;
    }

    public static void setBrowser(String _browser) {
       SeleniumBase.browser = _browser;
    }

    public String getTestName() {
    	return _testName;
    }
   
    public static WebDriver getDriver() {
        if (!isDriverInitialised() || _webDriver == null) {
        	initialiseDriver();
        }
        return _webDriver;
    }

    protected static void setDriver(WebDriver driver) {
        SeleniumBase._webDriver = driver;
    }
     
    protected static void setRemoteDriver(RemoteWebDriver driver) {
    	SeleniumBase._webDriver = driver;
    }

	/**
	 * Description: initialises WebDriver instance with browser driver specified in ITestEnvironment
	 * @author snakddlo
	 * @since 21 July 2014
	 */   
	protected static void initialiseDriver() {
        // Get the driver path from the helper
        OSDriverPathHelper.setDriverPathByCheckingOS();
        _driverPath = OSDriverPathHelper.getDriverPath();
        _driverLocation = OSDriverPathHelper.getDriverLocation();

		logger.info("Initialising WebDriver instance...");		
		if (getBrowser().equalsIgnoreCase("Firefox")) {
			setDriver(new FirefoxDriver());
			_driverInitialised = true;
			logger.info("driver initialised with Firefox driver");
		}
		else if (getBrowser().equalsIgnoreCase("Chrome")) {
			// specify the location of chromedriver.exe path
			System.setProperty("webdriver.chrome.driver", _driverPath);
            System.setProperty("webdriver.chrome.logfile", _driverLocation + "//chromedriver.log");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("test-type");
            options.addArguments("--disable-extensions");  // Added by bhattacharyad on 16/3/16
            DesiredCapabilities dc = DesiredCapabilities.chrome();
            dc.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.ACCEPT);
            dc.setCapability(ChromeOptions.CAPABILITY, options);
            setDriver(new ChromeDriver(options));

			_driverInitialised = true;
			logger.info("driver initialised with chrome driver.");
		} else if(getBrowser().equals("Remote")) {
            DesiredCapabilities dc = DesiredCapabilities.chrome();
            try {
                setDriver(new RemoteWebDriver(new URL(("http://192.168.99.100:4444/wd/hub")),dc));
            } catch (MalformedURLException e) {
               logger.error("URL for WebDriver is incorrect",e);
            }

            _driverInitialised = true;
            logger.info("driver initialised with remote driver.");
        }
		else if (getBrowser().equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", _driverPath);
			DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();  
			ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			setDriver(new InternetExplorerDriver(ieCapabilities));
			_driverInitialised = true;
			logger.info("driver initialised with InternetExplorerDriver");
		}
		else if (getBrowser().equalsIgnoreCase("HtmlUnitDriver")) {
			setDriver(new HtmlUnitDriver());
			_driverInitialised = true;
			logger.info("driver initialised with HTMLUnitDriver");
		}
	}
    
    protected void finaliseDriver() {
        try {
        	logger.info(">> FinaliseDriver() Finalising the driver...");
            getDriver().quit();
        } catch (WebDriverException ex) {
        	logger.error("FinaliseDriver() WebDriverException -> " + ex.getMessage() + ex.getStackTrace());
            CleanUpAfterException();
        }

        _webDriver = null;
        _driverInitialised = false;
        setSuiteMode(false);
    }

    // Captures screenshot and kills all driver instances and crash reporting windows
    public void CleanUpAfterException() {
        // TODO: Do clean up action in the case of an exception     
    }

    public static boolean isDriverInitialised() {
        return _driverInitialised;
    }

    public void closeBrowser() {
        if (_webDriver != null) {
        	_webDriver.close();
        	logger.info("Base > CloseBrowser(), Browser is closed");
        }
    }

    public void maximize() {
        if (_webDriver != null) _webDriver.manage().window().maximize();
    }

    public void navigateTo(String url) {
        if (_webDriver != null) {
            _webDriver.navigate().to(url);
        }
    }

    public static void TurnOnImplicitWait() {
        if (_webDriver != null) _webDriver.manage().timeouts().implicitlyWait(_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public void TurnOnImplicitWait(long seconds) {
        if (_webDriver != null) _webDriver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void TurnOffImplicitWait() {
        if (_webDriver != null) _webDriver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    }
       
	public static void log(String message) {
		Reporter.log(message + "<br>");
		logger.info(message + "<br>");
	}

	public static void log(String message, boolean takeScreenshot) {
		Reporter.log(message + "<br>");
		if (takeScreenshot)	TestBase.createScreenshot();
	}
	
	public void  pressEnter() {
		Keyboard kb = ((RemoteWebDriver) getDriver()).getKeyboard();
		kb.pressKey(Keys.RETURN);
	}

    public void  pressEscape() {
        Keyboard kb = ((RemoteWebDriver) getDriver()).getKeyboard();
        kb.pressKey(Keys.ESCAPE);
    }

    public void  pressBackspace() {
        Keyboard kb = ((RemoteWebDriver) getDriver()).getKeyboard();
        kb.pressKey(Keys.BACK_SPACE);
    }
}
 
