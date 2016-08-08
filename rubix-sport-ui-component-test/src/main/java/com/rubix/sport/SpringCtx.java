package com.rubix.sport;

import com.rubix.sport.steps.AbstractStepDef;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by payotj on 21/07/2016.
 */
@Configuration
@ComponentScan(basePackages = {"com.rubix.sports.framework","com.rubix.sport"},
        /*
            The Cucumber Spring runner will start its own context. If these are not excluded,
            then duplicate bean names will appear in the context.
         */
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        value = {AbstractStepDef.class}
                )
        }
)
@PropertySource(value="classpath:cucumber-test.properties")
public class SpringCtx {

    /**
     * Used to resolve ${value} for Spring
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}