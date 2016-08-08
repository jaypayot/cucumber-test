package com.rubix.sport;

/**
 * Class is used to execute Cucumber tests. Scans the lsportsnba.test package directory.
 */
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(format = { "json", "json:target/cucumber.json"},
        glue = {"com.rubix.sport"},
        strict = true,
        features="src/test/resources/features")
public class CucumberRunner {

}