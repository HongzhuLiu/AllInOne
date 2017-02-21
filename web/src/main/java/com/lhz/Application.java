package com.lhz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by lhz on 16-8-29.
 */

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableOAuth2Sso
public class Application{
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}