> 本项目记录Hystrix Turbine消费rabbitMQ中收集的信息进行集群监控

# Hystrix简介

Hystrix Dashboard是Hystrix的仪表盘组件，主要用来实时监控Hystrix的各项指标信息，通过界面反馈的信息可以快速发现系统中存在的问题。

注意： 
- Hystrix Dashboard是一个监控单体应用的例子，在实际应用中，我们要监控的应用往往是一个集群，这个时候我们就得采取Turbine集群监控了。Turbine有一个重要的功能就是汇聚监控信息，并将汇聚到的监控信息提供给Hystrix Dashboard来集中展示和监控。

# Turbine使用时注意事项：
## Turbine服务是个独立的结点，他需要配置eureka信息，将其注册到Eureka中，从Eureka中获取监控对象的信息，依赖以下jar包

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-turbine-stream</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-start-stream-rabbit</artifactId>
    </dependency>

## Turbine主程序也很简单，就是个标准的Spring boot应用，多了个@EnableTurbineStream
@EnableDiscoveryClient注解

    @SpringBootApplication
    @EnableTurbineStream    //启动数据收集功能  将收集的数据存储到rabbitMQ中去
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

    #配置rabbitMQ
    spring.rabbitmq.host=localhost
    spring.rabbitmq.port=5672
    spring.rabbitmq.username=guest
    spring.rabbitmq.password=guest


# 测试结果

- 启动Eureka注册中心：microservice-discovery-eureka
- 启动服务提供者：microservice-provider-user
- 启动服务消费者(turbine.app-config中配置的服务，多个)： microservice-consume-movie-feign-hystrix-turbine-mq
- 启动程序开启了断路器（@EnableCircuitBreaker注解）。
- 启动Turbine监控项目：microservice-hystrix-turbine-mq

注意：此项目需注册到Eureka中,会将监控数据汇集到rabbitMQ消息中间件中


- 访问注册中心： http://127.0.0.1:8761/  检查服务是否启动
- 访问服务消费者： http://192.168.224.1:9106/say2
- 访问监控面板： http://127.0.0.1:9094
