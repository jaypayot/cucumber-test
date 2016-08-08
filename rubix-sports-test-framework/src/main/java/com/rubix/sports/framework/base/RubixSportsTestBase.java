package com.rubix.sports.framework.base;

import com.luxbet.qa.framework.core.TestBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by payotj on 19/07/2016.
 */
@Component
public class RubixSportsTestBase extends TestBase{

    @Autowired
    RubixSportsTestProperties rubixSportsTestProperties;

    private String _username;
    private String _password;
    private String _baseurl;
    private String _browser;

    @PostConstruct
    public void setup() {
        initializeParamsFromProfile();
    }

    @PreDestroy
    public void destroy() {
        finaliseDriver();
    }

    private void initializeParamsFromProfile() {
        setBrowser(rubixSportsTestProperties.getBrowser());
        setBaseUrl(rubixSportsTestProperties.getBaseUrl());
        setUserName(rubixSportsTestProperties.getUsername());
        setPassword(rubixSportsTestProperties.getPassword());
    }

    //** Getters and Setters **//
    public String getUsername() {
        return this._username;
    }

    public void setUserName(String userName) {
        this._username = userName;
    }
    public String getPassword() {
        return this._password;
    }

    public void setPassword(String password) {
        this._password = password;
    }

    public void setBaseUrl(String baseurl) {
        this._baseurl = baseurl;
    }

    @Override
    public String getBaseUrl() {
        return this._baseurl;
    }

//    public void setBrowser(String browser) {
//        this._browser = browser;
//    }

//    @Autowired
//    private SharedDriver sharedDriver;

//    @Before
//    public void deleteAllCookies() {
//        this.sharedDriver.manage().deleteAllCookies();
//    }

//    @After()
//    public void embedScreenshotIfFailed(Scenario scenario) {
//        if (scenario.isFailed()) {
//            try {
//                scenario.write("Current Page URL is " + sharedDriver.getCurrentUrl());
//                if (sharedDriver.getWrappedDriver() instanceof TakesScreenshot) {
//                    byte[] screenshot = sharedDriver.getScreenshotAs(OutputType.BYTES);
//                    scenario.embed(screenshot, "image/png");
//                }
//            } catch (Throwable somePlatformsDontSupportScreenshotsOrBrowserHasDied) {
//                somePlatformsDontSupportScreenshotsOrBrowserHasDied.printStackTrace(System.err);
//            }
//        }
//    }

    public IRubixSports RubixSports(){
        return new RubixSports();
    }

    public void startTest() {
        initialiseTest("","");
    }
}
