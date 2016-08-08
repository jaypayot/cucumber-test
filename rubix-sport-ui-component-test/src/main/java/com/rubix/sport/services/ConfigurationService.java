package com.rubix.sport.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Components contains configurable values loaded from the functionalTest.properties file.
 */
@Component
public class ConfigurationService {

    @Value("${wiremock.host}")
    private String wiremockHost;

    @Value("${wiremock.port}")
    private String wiremockPort;

    @Value("${rubix-sports-base-url}")
    private String rubixSportUrl;

    public Integer getWiremockPort() {
        return new Integer(wiremockPort);
    }

    public String getWiremockHost() {
        return wiremockHost;
    }

    public String getRubixSportUrl() {
        return rubixSportUrl;
    }
}
