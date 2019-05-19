package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import cn.hutool.core.date.DateUtil;

@SpringBootApplication
// 增加Eureka客户端功能
@EnableEurekaClient
public class UserProviderApplication {

	public static void main(String[] args) {

		System.out.println("HHHH" + DateUtil.today());

		SpringApplication.run(UserProviderApplication.class, args);
	}

}
