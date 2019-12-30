package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务注册中心
 * 
 * @author Taoweidong
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {

    /**
     * 启动类
     * 
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class, args);
    }

}
