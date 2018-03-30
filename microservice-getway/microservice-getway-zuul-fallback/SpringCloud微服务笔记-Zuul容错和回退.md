# Zuul简介

是Netflix开源的微服务网关，他可以和Eureka,Ribbon,Hystrix等组件配合使用。Zuul组件的核心是一系列的过滤器，这些过滤器可以完成以下功能：

- 身份认证和安全: 识别每一个资源的验证要求，并拒绝那些不符的请求
- 审查与监控：
- 动态路由：动态将请求路由到不同后端集群
- 压力测试：逐渐增加指向集群的流量，以了解性能
- 负载分配：为每一种负载类型分配对应容量，并弃用超出限定值的请求
- 静态响应处理：边缘位置进行响应，避免转发到内部集群
- 多区域弹性：跨域AWS Region进行请求路由，旨在实现ELB(ElasticLoad Balancing)使用多样化，以及让系统的边缘更贴近系统的使用者。
	
Spring Cloud对Zuul进行了整合和增强。目前，Zuul使用的默认是Apache的HTTP Client，也可以使用Rest Client，可以设置ribbon.restclient.enabled=true.

在SpringCloud中，Zuul默认已经整合了Hystrix。

# 测试Zuul的Hystrix功能

- 启动服务注册中心项目：microservice-discovery-eureka
- 启动服务提供者项目：microservice-provider-user
- 启动路由网关项目： microservice-getway-zuul
- 启动项目：microservice-hystrix-dashboard
- 访问：http://127.0.0.1:8761/  检查服务是否启动成功
- 访问：http://127.0.0.1:8040/microservice-provider-user/hello   可获得正常结果
- 访问：http://127.0.0.1:8040/hystrix.stream   可获得监控数据
- 访问：http://127.0.0.1:9090/hystrix,监控栏位填入http://127.0.0.1:8040/hystrix.stream，可打开监控界面

可知： Zuul的Hystrix的监控粒度是微服务级别的，而不是某个API；同时也说明，经过Zuul的请求，都会被Hystrix保护起来


# 为Zuul添加回退功能

> 想要为Zuul添加回退功能，需要实现ZuulFallBackProvider接口。在接口的实现类中，指定为哪个微服务提供回退功能，并提供一个ClientHttpResponse作为回退响应

## 创建工程microservice-getway-zuul-fallback，添加依赖

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


## 编写回退类

	package com.taowd.fallback;
	
	import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
	import org.springframework.http.HttpHeaders;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.MediaType;
	import org.springframework.http.client.ClientHttpResponse;
	import org.springframework.stereotype.Component;
	import java.io.ByteArrayInputStream;
	import java.io.IOException;
	import java.io.InputStream;
	
	/**
	 * @author Taowd
	 * @date 2018/3/28 - 13:34
	 * @Description
	 */
	@Component
	public class UserFallBackProvider implements ZuulFallbackProvider {
	
	    @Override
	    public String getRoute() {
	        //表明为那个微服务提供回退功能
	        return "microservice-provider-user";
	    }
	
	    @Override
	    public ClientHttpResponse fallbackResponse() {
	        return new ClientHttpResponse() {
	            @Override
	            public HttpStatus getStatusCode() throws IOException {
	                return HttpStatus.OK;
	            }
	
	            @Override
	            public int getRawStatusCode() throws IOException {
	                return 200;
	            }
	
	            @Override
	            public String getStatusText() throws IOException {
	                return "OK";
	            }
	
		            @Override
		            public void close() {
		
		            }
		
		            @Override
		            public InputStream getBody() throws IOException {
		                return new ByteArrayInputStream("用户微服务不可用，请稍后再试。".getBytes());
		            }
		
		            @Override
		            public HttpHeaders getHeaders() {
		                HttpHeaders headers = new HttpHeaders();
		                headers.setContentType(MediaType.APPLICATION_JSON);
		                return headers;
		            }
		        };
		    }
		}




## 测试：路由规则


- 启动服务注册中心项目：microservice-discovery-eureka
- 启动服务提供者项目：microservice-provider-user
- 启动路由网关项目： microservice-getway-zuul-fallback
- 访问：http://127.0.0.1:8761/  检查服务是否启动成功
- 访问：http://169.254.223.131:8043/microservice-provider-user/hello   检查服务是否成功
- 停止服务：microservice-provider-user
- 访问：http://169.254.223.131:8043/microservice-provider-user/hello   检查是否返回回退信息

![](https://i.imgur.com/l6r6a0D.png)