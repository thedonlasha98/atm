package com.egs.atm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class Config {

    @Value("${egs.app.bank.username}")
    private String username;

    @Value("${egs.app.bank.password}")
    private String password;

    @Bean
    public RestTemplate getRestTemplate(
            RestTemplateBuilder restTemplateBuilder) {

        return restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(1000))
                .setReadTimeout(Duration.ofSeconds(1000))
                .interceptors(new BasicAuthorizationInterceptor(username, password))
                .build();
    }
}
