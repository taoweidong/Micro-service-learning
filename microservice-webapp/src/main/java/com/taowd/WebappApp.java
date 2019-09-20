package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author Taoweidong
 */
@SpringBootApplication
@EnableEurekaClient
public class WebappApp {

	public static void main(String[] args) {

		SpringApplication.run(WebappApp.class, args);
	}

}