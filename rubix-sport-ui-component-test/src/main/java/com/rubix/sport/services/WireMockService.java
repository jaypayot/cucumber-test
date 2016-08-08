package com.rubix.sport.services;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Handles the interaction with a standalone WireMock instance.
 * <p>
 */
@Service
public class WireMockService {
    private Logger logger = LoggerFactory.getLogger(WireMockService.class);

    @Autowired
    private ConfigurationService config;

    @Autowired
    FreemarkerService freeMarker;

    private WireMock mock;

    /**
     * Configures the Wiremock instance. Also resets all mappings, scenarios and requests within the connected
     * Wiremock instance.
     */
    @PostConstruct
    public void init() {
        final String host = this.config.getWiremockHost();
        final Integer port = this.config.getWiremockPort();
        mock = new WireMock(host, port);

        mock.resetMappings();
        mock.resetRequests();
        mock.resetScenarios();
        mock.resetToDefaultMappings();
    }


}
