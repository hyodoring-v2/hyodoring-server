package com.v2.hyodoring.account.core.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.v2.hyodoring")
public class FeignConfig {
}
