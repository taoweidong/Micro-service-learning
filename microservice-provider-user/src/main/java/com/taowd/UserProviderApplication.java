package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import cn.hutool.core.date.DateUtil;

/**
 * @author Taoweidong
 */
@SpringBootApplication
@EnableEurekaClient
public class UserProviderApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserProviderApplication.class, args);
	}

}
