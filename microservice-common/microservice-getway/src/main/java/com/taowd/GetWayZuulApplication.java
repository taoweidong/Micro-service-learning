package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关服务
 * @EnableZuulProxy 开启Zuul网关功能
 * @author Taoweidong
 */
@SpringBootApplication
@EnableZuulProxy
public class GetWayZuulApplication {

	/**
	 * 启动类
	 * @param args
	 */
	public static void main(String[] args) {

		SpringApplication.run(GetWayZuulApplication.class, args);
	}
}