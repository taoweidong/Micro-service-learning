> 本项目记录在Hystrix的基础上整合Hystrix Turbine进行集群监控

# Hystrix简介

Hystrix Dashboard是Hystrix的仪表盘组件，主要用来实时监控Hystrix的各项指标信息，通过界面反馈的信息可以快速发现系统中存在的问题。

注意： 
- Hystrix Dashboard是一个监控单体应用的例子，在实际应用中，我们要监控的应用往往是一个集群，这个时候我们就得采取Turbine集群监控了。Turbine有一个重要的功能就是汇聚监控信息，并将汇聚到的监控信息提供给Hystrix Dashboard来集中展示和监控。

# Turbine使用时注意事项：
## Turbine服务是个独立的结点，他需要配置eureka信息，将其注册到Eureka中，从Eureka中获取监控对象的信息，依赖以下jar包

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-turbine</artifactId>
    </dependency>

## Turbine主程序也很简单，就是个标准的Spring boot应用，多了个@EnableTurbine
@EnableDiscoveryClient注解

	@SpringBootApplication
	@EnableTurbine
	@EnableDiscoveryClient
	public class HystrixTurbineApplication {
	    /**
	     * 主函数
	     *
	     * @param args
	     */
	    public static void main(String[] args) {
	        SpringApplication.run(HystrixTurbineApplication.class, args);
	    }
	}

## 配置信息

注意：此处需要配置Eureka的信息

	# 应用启动的端口号
	server.port=9093
	#应用程序名称
	spring.application.name=microservice-hystrix-turbine
	
	#表示集群的名字为default
	turbine.cluster-name-expression="default"  
	#表示同一主机上的服务通过host和port的组合来进行区分，默认情况下是使用host来区分，这样会使本地调试有问题
	turbine.combine-host-port=true  
	#指定了要监控的应用名字为 MICROSERVICE-CONSUME-MOVIE-FEIGN-HYSTRIX
	turbine.app-config=MICROSERVICE-CONSUME-MOVIE-FEIGN-HYSTRIX
	
	#Eureka的相关配置
	#Eureka的健康检查
	#eureka.client.healthcheck.enabled=true
	#如果可以在配置时确定主机名（否则将从操作系统原语中猜出）
	eureka.instance.hostname=microservice-hystrix-turbine
	#表示将自己的IP注册到Eureka Server。如果不配置会将操作系统的hostname到Eureka Server.
	eureka.instance.prefer-ip-address=true
	# Eureka服务器地址也就是注册中心的地址 包含认证账号和密码
	eureka.client.serviceUrl.defaultZone=http://admin:admin123@localhost:8761/eureka
	# 指示该客户端是否应从eureka服务器获取eureka注册表信息。 默认值为true   一般为服务消费者配置
	eureka.client.fetch-registry=true


# 测试结果

- 启动Eureka注册中心：microservice-discovery-eureka
- 启动服务提供者：microservice-provider-user
- 启动服务消费者(turbine.app-config中配置的服务，多个)： microservice-consume-movie-feign-hystrix  其中包括Hystrix熔断机制的方法回退

注意：此项目必须要确保被监控的服务打开了Actuator（依赖spring-boot-starter-actuator），

- 启动程序开启了断路器（@EnableCircuitBreaker注解）。
- 启动Turbine监控项目：microservice-hystrix-turbine

注意：此项目需注册到Eureka中

- 启动Hystrix DashBoard监控项目： microservice-hystrix-dashboard  
注意：此项目无需注册到Eureka中
- 访问注册中心： http://127.0.0.1:8761/  检查服务是否启动
- 访问服务消费者： http://127.0.0.1:9103/say2
- 访问监控面板： http://127.0.0.1:9090/hystrix
- 填写相应参数: http://192.168.224.1:9093/turbine.stream

![](https://i.imgur.com/hH1zyeW.png)

通过Hystrix Dashboard主页面的文字介绍，我们可以知道，Hystrix Dashboard共支持三种不同的监控方式

- 默认的集群监控：通过URL:http://turbine-hostname:port/turbine.stream开启，实现对默认集群的监控。
- 指定的集群监控：通过URL:http://turbine-hostname:port/turbine.stream?cluster=[clusterName]开启，实现对clusterName集群的监控。
- 单体应用的监控：通过URL:http://hystrix-app:port/hystrix.stream开启，实现对具体某个服务实例的监控。
- Delay：控制服务器上轮询监控信息的延迟时间，默认为2000毫秒，可以通过配置该属性来降低客户端的网络和CPU消耗。
- Title:该参数可以展示合适的标题。

![](https://i.imgur.com/FtDab7g.png)

![](https://i.imgur.com/wpkTi62.png)