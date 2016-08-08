package com.rubix.sports.framework.springcontext;

import com.rubix.sports.framework.base.RubixSportsTestProperties;
import com.rubix.sports.framework.webdriver.SharedDriver;
import org.springframework.context.annotation.Bean;

/**
 * Created by payotj on 25/07/2016.
 */

//@Configuration
//@PropertySource(value = {"classpath:/cucumber-test.properties"})
public class RubixSportsSpringContext {

    @Bean
    public SharedDriver driver() {
        return new SharedDriver();
    }

    @Bean
    public RubixSportsTestProperties properties(){
        return new RubixSportsTestProperties();
    }
}
