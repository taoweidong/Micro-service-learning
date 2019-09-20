package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka服务注册中心
 * @author Taoweidong
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {

	public static void main(String[] args) {

		SpringApplication.run(EurekaApp.class, args);
	}

}
