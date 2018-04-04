## 为什么需要自动刷新配置

微服务的配置可以通过Spring Cloud Config 组件来统一管理微服务的配置，实现集中式的配置管理功能。随着系统的不断扩展，通过使用/refresh端点手动刷新配置的功能将不在可行，因为工作量过大；随着系统的不断扩展，会越来越难以维护，因此实现配置的自动刷新是很有必要的

## Spring Cloud Bus简介

Spring Cloud Bus使用轻量级的消息代理(如;RabbitMQ,Kafka等)连接分布式系统的节点，这样就可以以广播的传播状态的更改(例如配置的更新)或者其他的管理指令。Spring Cloud Bus就类似于一个分布式的Spring Boot Actuator

部署后所有的微服务实例都通过消息总线连接到一起，每个实例都会订阅配置更新事件，当其中的一个微服务节点的/bus/refresh端点被请求时，该实例就会向消息总线发送一个配置更新事件，其他实例获得该事件后也会更新配置。

## 具体实现

- 安装RabbitMQ
- 创建项目：microservice-config-client-refresh-cloud-bus
- 添加依赖

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
   <dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
- 修改配置文件：boostrap.yml

```
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
```



