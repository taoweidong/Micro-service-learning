# Spring Cloud Config 简介

Spring Cloud Config 为分布式系统外部化配置提供了服务器和客户端的支持，它包括Config Server和Config Client两部分。由于Config Server 和Config Client都实现了对于Spring Environment和PropertySource抽象的映射。

- Config Server是一个可横向扩展，集中式的配置服务器，它用于集中管理应用程序各个环境下的配置，默认使用Git存储配置内容。
- Config Client是Config Server的客户端，用于操作存储在Config Server中的配置属性。

![](https://img-blog.csdn.net/20171112173144032?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbWF6aGVuMTk5MQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 编写一个Config Server

## 创建git仓库作为后端存储

![](C:\Users\Taowd\Pictures\ZUUL\09.png)

## 创建工程，添加依赖

	<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-config-server</artifactId>
	</dependency>

## 编写启动类，添加注解类

	@SpringBootApplication
	@EnableConfigServer
	public class ConfigServerApplication {
	
	    public static void main(String[] args) {
	        SpringApplication.run(ConfigServerApplication.class, args);
	    }
	}

## 编写配置文件

	# 应用启动的端口号
	server.port=8989
	#应用程序名称
	spring.application.name=microservice-consume-movie
	
	#Config Server配置
	spring.cloud.config.server.git.uri=https://gitee.com/taowd/spring-cloud-repo
	spring.cloud.config.server.git.username=用户名
	spring.cloud.config.server.git.password=密码

## 启动服务

访问：http://127.0.0.1:8989/application/dev

![](https://i.imgur.com/sQVZYsL.png)