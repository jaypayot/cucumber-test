package com.rubix.sport.definition.login;

import com.rubix.sport.steps.AbstractStepDef;
import com.rubix.sports.framework.base.RubixSportsTestBase;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.Test;

/**
 * Created by payotj on 25/07/2016.
 */

public class LoginStepDefinition extends AbstractStepDef {
    public static final String RUBIX_CREDENTIALS_PREFIX = "rubix.sports.credentials.";
    @Autowired
    RubixSportsTestBase rubixSportsTestBase;

    @Autowired
    ApplicationContext ctx;

    @Test
    @Given("^I launch Rubix Sports homepage$")
    public void iLaunchRubixSportsHomepage() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
            rubixSportsTestBase.startTest();
    }

    @When("^I enter valid \"([^\"]*)\" in username field$")
    public void iEnterValidInUsernameField(String usernamePlaceholder) throws Throwable {
        String username = getCredentialByName(usernamePlaceholder);
        // Write code here that turns the phrase above into concrete actions
        rubixSportsTestBase.RubixSports().LoginPage().Username().Set(username);
    }

    @And("^enter valid \"([^\"]*)\" in the password field$")
    public void enterValidInThePasswordField(String passwordPlaceholder) throws Throwable {
        String password = getCredentialByName(passwordPlaceholder);
        // Write code here that turns the phrase above into concrete actions
        rubixSportsTestBase.RubixSports().LoginPage().Password().Set(password);
        Thread.sleep(900);
    }

    @And("^click the login button$")
    public void clickTheLoginButton() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Thread.sleep(900);
        rubixSportsTestBase.RubixSports().LoginPage().LoginButton().Click();
        Thread.sleep(900);
    }

    @Then("^the homepage background color is \"([^\"]*)\"$")
    public void theHomepageBackgroundColorIs(String color) throws Throwable {
        rubixSportsTestBase.RubixSports().MainPage().VerifyBackgroundColor(color);
        Thread.sleep(900);
    }

    private String getCredentialByName(String propertyName) {
        return ctx.getEnvironment().getProperty(RUBIX_CREDENTIALS_PREFIX + propertyName);
    }

    @And("^I logout from Rubix Sports$")
    public void iLogoutFromRubixSports() throws Throwable {
        Thread.sleep(900);
        // Write code here that turns the phrase above into concrete actions
       rubixSportsTestBase.RubixSports().MainPage().Logout().Click();
    }
}
