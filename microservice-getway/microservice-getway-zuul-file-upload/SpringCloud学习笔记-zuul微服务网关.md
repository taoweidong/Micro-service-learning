>Zuul是Netflix开源的微服务网关，他可以和Eureka,Ribbon,Hystrix等组件配合使用。Zuul组件的核心是一系列的过滤器，这些过滤器可以完成以下功能：

    • 身份认证和安全: 识别每一个资源的验证要求，并拒绝那些不符的请求
    • 审查与监控：
    • 动态路由：动态将请求路由到不同后端集群
    • 压力测试：逐渐增加指向集群的流量，以了解性能
    • 负载分配：为每一种负载类型分配对应容量，并弃用超出限定值的请求
    • 静态响应处理：边缘位置进行响应，避免转发到内部集群
    • 多区域弹性：跨域AWS Region进行请求路由，旨在实现ELB(ElasticLoad Balancing)使用多样化，以及让系统的边缘更贴近系统的使用者。
	
Spring Cloud对Zuul进行了整合和增强。目前，Zuul使用的默认是Apache的HTTP Client，也可以使用Rest Client，可以设置ribbon.restclient.enabled=true.

# 编写Zuul微服务网关

## 创建工程，添加依赖

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

## 添加注解

在启动类上添加注解@EnableZuulProxy，声明了一个Zuul代理。该代理使用Ribbon来定位注册在Eureka Server中的微服务；同时，该代理还整合了Hystrix，从而实现了容错，所有经过Zuul的请求都会在Hystrix命令中执行。

	@SpringBootApplication
	@EnableZuulProxy//开启Zuul网关功能
	publicclassGetWayZuulApplication{
	publicstaticvoidmain(String[]args){
		SpringApplication.run(GetWayZuulApplication.class,args);
		}
	}


## 编写配置文件

从配置文件中可知，此处仅仅是添加了Zuul的依赖，并将Zuul注册到Eureka Server上。

	#应用启动的端口号
	server.port=8040
	#应用名称
	spring.application.name=microservice-getway-zuul

	#Eureka的相关配置
	#如果可以在配置时确定主机名（否则将从操作系统原语中猜出）
	#eureka.instance.hostname=microservice-getway-zuul
	#表示将自己的IP注册到EurekaServer。如果不配置会将操作系统的hostname到EurekaServer.
	eureka.instance.prefer-ip-address=true
	#Eureka服务器地址也就是注册中心的地址包含认证账号和密码
	eureka.client.serviceUrl.defaultZone=http://admin:admin123@localhost:8761/eureka


## 测试：路由规则


- 启动服务注册中心项目：microservice-discovery-eureka
- 启动服务提供者项目：microservice-provider-user
- 启动服务消费者项目：microservice-consume-movie-ribbon
- 启动路由网关项目： microservice-getway-zuul
- 访问：http://127.0.0.1:8761/  检查服务是否启动成功
- 访问：http://127.0.0.1:8040/microservice-consume-movie-ribbon/say2   检查服务是否成功
- 访问：http://127.0.0.1:8040/microservice-provider-user/getUser   检查服务是否成功

**Zuul路由规则：http://ZUUL_HOST:ZUUL_PORT/微服务在Eureka上的serviceId/**会被转发到serviceId对应的微服务上**


## 测试：负载均衡功能
	1. 启动服务注册中心：microservice-discovery-eureka
	2. 启动多个服务提供者：microservice-provider-user(端口不同)
	3. 启动路由网关项目：microservice-getway-zuul
	4. 访问：http://127.0.0.1:8761/  检查服务是否启动成功


多次访问192.168.224.1:8040/microservice-provider-user/hello，会发现两个服务提供者循环显示，说明Zuul可以使用Ribbon达到负载均衡的效果

## 测试：Hystrix容错与监控功能
	1. 启动服务注册中心项目：microservice-discovery-eureka
	2. 启动服务提供者项目：microservice-provider-user
	3. 启动服务消费者项目：microservice-consume-movie-ribbon
	4. 启动路由网关项目： microservice-getway-zuul
	5. 启动服务监控项目： microservice-hystrix-dashboard
	6. 访问：http://127.0.0.1:8761/  检查服务是否启动成功
	7. 访问：192.168.224.1:8040/microservice-consume-movie-feign-hystrix3/say2 获得预期效果
	8. 访问服务监控：http://127.0.0.1:9090/hystrix  输入：http://192.168.224.1:8040/hystrix.stream(网关地址)，结果显示


说明：Zuul已经整合Hystrix功能

## Zuul路由端口

当@EnableZuulProxy与Spring Boot Actuator配置使用时，Zuul会暴露一个路由管理端点/routes。借助这个端点。可以很方便的直观的查看以及管理Zuul的路由。
/routes端点的使用非常简单，使用get方法访问该端点，即可放回Zuul当前映射的路由列表；使用POST方法访问该端点就会强制刷新Zuul当前映射的路由列表(尽管路由会自动刷新，SpringCloud依然提供了立即刷新的方式)。
由于Spring-Cloud-starter-zuul已经包含了Spring-Boot-Starter-actuator，因此之前编写的路由网关项目已经具有路由管理的能力。

## 测试路由网关的管理功能：
	1. 启动服务注册中心项目：microservice-discovery-eureka
	2. 启动服务提供者项目：microservice-provider-user
	3. 启动服务消费者项目：microservice-consume-movie-ribbon
	4. 启动路由网关项目： microservice-getway-zuul
	5. 访问：http://192.168.224.1:8040/routes


## 路由配置详解

### 自定义指定微服务的访问路径：
----------
	#自定义指定微服务的访问路径
	zuul.routes.microservice-provider-user=/user/**
	这样设置，microservice-provider-user微服务就会被映射到/user/**的路径

### 忽略指定微服务

----------

	#忽略指定微服务,多个是可用逗号分隔开
	zuul.ignored-services=microservice-consume-movie-feign-hystrix3,microservice-consume-movie-feign-hystrixzuu


### 忽略所有微服务，只路由代理指定的微服务

----------
	#忽略所有微服务，只路由代理指定的微服务
	zuul.ignored-services='*'
	zuul.routes.microservice-provider-user=/user/**
	这样就可以让Zuul只路由microservice-provider-user微服务

### 同时指定path和URL

----------

	#同时指定path和URL
	zuul.routes.user-routes.url=http://127.0.0.1:9001/
	zuul.routes.user-routes.path=/user/**

### 同时指定path和URL，并且不破坏Zuul的Hystrix，Ribbon特性

