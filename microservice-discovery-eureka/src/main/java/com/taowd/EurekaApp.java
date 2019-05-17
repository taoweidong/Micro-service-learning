package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.ArrayList;
import java.util.List;

/**
 * Eureka服务注册中心
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Hello");
        System.out.println(list);


        SpringApplication.run(EurekaApp.class, args);
    }
}
