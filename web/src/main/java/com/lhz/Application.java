package com.lhz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主应用程序类 - Web项目的入口点
 *
 * 该类负责启动Spring Boot应用程序，并启用了以下功能：
 * - Spring Boot自动配置
 * - 缓存支持
 * - 定时任务支持
 * - OAuth2单点登录
 *
 * @author lhz
 * @since 2016-08-29
 */

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableOAuth2Sso
public class Application{
    /**
     * 应用程序的入口点方法
     *
     * 该方法使用SpringApplication.run()启动Spring Boot应用程序
     *
     * @param args 命令行参数
     * @throws Exception 启动过程中可能抛出的异常
     */
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}