package org.appcomponents.platform.configuration;

import org.appcomponents.platform.mvc.PlatformRequestMappingHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Martin Janys
 */
@EnableWebMvc
@Configuration
public class PlatformConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerMapping platformHandlerMapping() {
        return new PlatformRequestMappingHandlerMapping();
    }
}
