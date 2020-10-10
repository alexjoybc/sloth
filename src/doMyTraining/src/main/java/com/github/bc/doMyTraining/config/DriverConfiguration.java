package com.github.bc.doMyTraining.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({NttProperties.class})
public class DriverConfiguration {
}
