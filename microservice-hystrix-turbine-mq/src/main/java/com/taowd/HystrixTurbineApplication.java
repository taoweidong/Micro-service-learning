package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.turbine.stream.EnableTurbineStream;

@SpringBootApplication
@EnableTurbineStream    //启动数据收集功能  将收集的数据存储到rabbitMQ中去
@EnableDiscoveryClient
public class HystrixTurbineApplication {
    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(HystrixTurbineApplication.class, args);
    }
}


