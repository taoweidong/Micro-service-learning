package com.taowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import cn.hutool.core.date.DateUtil;

/**
 * @author Taoweidong
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.taowd.mapper")
public class UserProviderApplication {

  public static void main(String[] args) {

    SpringApplication.run(UserProviderApplication.class, args);
  }

}
