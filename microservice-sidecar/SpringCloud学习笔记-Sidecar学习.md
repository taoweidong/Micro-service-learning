# Sidecar简介

Sidecar是作为一个代理的服务来间接性的让其他语言可以使用Eureka等相关组件。通过与Zuul的来进行路由的映射，从而可以做到服务的获取，然后可以使用Ribbon，Feign对服务进行消费，以及对Config Server的间接性调用。

# 官网介绍

你是否想要在非jvm的语言中利用（间接性使用）Eureka，Ribbon以及Config Server？Spring Cloud Netflix Sidecar的设计灵感来自Netflix Prana。它包含一个简单的http api去获取一个已知服务的所有实例(例如主机和端口)。你也可以通过嵌入的Zuul代理(Zuul中有一个代理功能)对代理的服务进行调用，Zuul从Eureka服务注册中心获取所有的路由记录(route entries)。通过host发现(host lookup)或者Zuul代理可以直接访问Spring Cloud Config。非jvm需要应该实现一个健康检查，Sidecar能够以此来报告给Eureka注册中心该应用是up还是down状态。

# 使用

- 在你的项目中使用Sidecar，需要添加依赖，其group为org.springframework.cloud，artifact id为spring-cloud-netflix-sidecar。(这是以maven依赖的方式)

- 启用Sidecar，创建一个Spring Boot应用程序，并在在应用主类上加上@EnableSidecar注解。该注解包含@EnableCircuitBreaker, @EnableDiscoveryClient以及@EnableZuulProxy。Run the resulting application on the same host as the non-jvm application. (这句不太会翻译，我的理解为：在与非jvm应用程序相同的主机上运行生成的应用程序)注：这里的生成应该是通过代理产生的服务。

- 配置Sidecar，在application.yml中添加sidecar.port和sidecar.health-uri。sidecar.port属性是非jre程序监听的端口号，这就是Sidecar可以正确注册应用到Eureka的原因。sidecar.health-uri是非jre应用提供的一个对外暴露的可访问uri地址，在该地址对应的接口中需要实现一个模仿Spring Boot健康检查指示器的功能。它需要返回如下的json文档。(注：通过返回一个json，其用status字段来标识你的应用的服务状态，是up还是down，sidecar会将该状态报告给eureka注册中心从而实现你的服务的状态可用情况。简单的说就是用来控制sidecar代理服务的状态！)

# 具体步骤

## 创建工程(microservice-sidecar)

## 添加POM依赖

	 <!--Api网关依赖-->
	<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-zuul</artifactId>
	</dependency>
	
	<!--添加Eureka的jar包-->
	<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
	
	<!--sidecar依赖-->
	<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-netflix-sidecar</artifactId>
	</dependency>

## 启动类添加注解

	@SpringBootApplication
	@EnableSidecar   //开启Sidecar功能
	public class GetWayZuulApplication {
	
	    public static void main(String[] args) {
	        SpringApplication.run(GetWayZuulApplication.class, args);
	    }
	}


## 配置文件

	# 应用启动的端口号
	server.port=8044
	#应用名称
	spring.application.name=microservice-sidecar
	
	#Eureka的相关配置
	#如果可以在配置时确定主机名（否则将从操作系统原语中猜出）
	#eureka.instance.hostname=microservice-getway-zuul
	#表示将自己的IP注册到Eureka Server。如果不配置会将操作系统的hostname到Eureka Server.
	eureka.instance.prefer-ip-address=true
	# Eureka服务器地址也就是注册中心的地址 包含认证账号和密码
	eureka.client.serviceUrl.defaultZone=http://admin:admin123@localhost:8761/eureka
	
	#Sidecar配置
	#Node.js微服务的端口
	sidecar.port=8060
	# Node.js微服务的健康检查URL地址
	sidecar.health-uri=http://127.0.0.1:8060/health.json


# 编写非JVM服务(Node.js服务)

	var http = require('http');
	var url = require("url");
	var path = require('path');
	
	// 创建server
	var server = http.createServer(function(req, res) {
	    // 获得请求的路径
	    var pathname = url.parse(req.url).pathname;
	    res.writeHead(200, { 'Content-Type' : 'application/json; charset=utf-8' });
	    // 访问http://localhost:8060/，将会返回{"index":"欢迎来到首页"}
	    if (pathname === '/') {
	        res.end(JSON.stringify({ "index" : "欢迎来到首页" }));
	    }
	    // 访问http://localhost:8060/health，将会返回{"status":"UP"}
	    else if (pathname === '/health.json') {
	        res.end(JSON.stringify({ "status" : "UP" }));
	    }
	    // 其他情况返回404
	    else {
	        res.end("404");
	    }
	});
	// 创建监听，并打印日志
	server.listen(8060, function() {
	    console.log('listening on localhost:8060');
	});


# 测试

- 启动服务注册中心项目：microservice-discovery-eureka
- 启动路由网关项目： microservice-getway-zuul
- 启动Node.js服务：node node-service.js

![](https://i.imgur.com/6myCurc.png)

- 启动Sidecar项目： microservice-sidecar
- 访问：http://127.0.0.1:8761/  检查服务是否启动成功
- 访问：http://127.0.0.1:8040/microservice-sidecar 显示Node.js服务器内容

![](https://i.imgur.com/OEYOjBz.png)


