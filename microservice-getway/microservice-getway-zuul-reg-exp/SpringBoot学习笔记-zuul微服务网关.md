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
