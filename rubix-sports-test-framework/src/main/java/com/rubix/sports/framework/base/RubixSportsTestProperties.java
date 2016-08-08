package com.rubix.sports.framework.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Used to retrieve properties and make available during test
 * Created by payotj on 19/07/2016.
 */

@Configuration
@PropertySource(value = {"classpath:/cucumber-test.properties"})
public class RubixSportsTestProperties {

    @Value("${rubix-sports-base-url}")
    private String baseUrl;

    @Value("${rubix-sports-username}")
    private String username;

    @Value("${rubix-sports-password}")
    private String password;

    @Value("${rubix-sports-browser}")
    private String browser;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBrowser() {
        return browser;
    }

}
