package com.hyw.platform.web.syswebconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com.hyw.platform.web.controller")
public class SpringMvcAutoConfigure {

    @Bean
    @ConditionalOnMissingBean(WebLogAspect.class)
    public WebLogAspect webLogAspect() {
        return new WebLogAspect();
    }
}
