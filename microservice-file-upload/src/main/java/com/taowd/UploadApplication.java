package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
//增加Eureka客户端功能
@EnableEurekaClient
public class UploadApplication {

    public static void main(String[] args) {

        SpringApplication.run(UploadApplication.class, args);
    }


}
