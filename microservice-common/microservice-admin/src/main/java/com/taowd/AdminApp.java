package com.taowd;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 服务监控服务
 * @author Taoweidong
 * @EnableAdminServer 开启服务监控服务端
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
public class AdminApp {

  /**
   * 启动类
   * @param args
   */
  public static void main(String[] args) {

    SpringApplication.run(AdminApp.class, args);
  }
}
