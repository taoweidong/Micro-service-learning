package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient //启动Eureka客户端功能
@EnableFeignClients //启动Feign客户端功能
//@RibbonClient  //启动Ribbon客户端负载均衡功能  参数name可指定微服务进行负载均衡
// 注意：Feign中已经包含Ribbon此处不用专门开启Ribbon功能
public class MovieConsumeApplication {
    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MovieConsumeApplication.class, args);
    }
}
