package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient//Eureka中已经集成Ribbon的功能，无需再引入jar包
//自定义Ribbon客户端，指定此name的微服务使用configuration的负载均衡策略
@RibbonClient(name = "microservice-provider-user", configuration = RibbonConfiguration.class)
public class MovieConsumeRibbonApplication {

    @Bean
    @LoadBalanced  //添加Ribbon负载均衡功能(默认使用轮询机制)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MovieConsumeRibbonApplication.class, args);
    }
}
