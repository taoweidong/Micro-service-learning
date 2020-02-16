# Spring-Boot-Admin介绍

简单来说，`Spring Boot Admin`是一个管理和监控`Spring Boot`应用程序的开源软件。每个应用都认为是一个客户端，通过`HTTP`或者`服务注册发现Spring Cloud`(Eureka、Consul等等)注册到`admin server`中进行展示，`Spring Boot Admin UI`部分使用`AngularJs`将数据展示在前端。

`Spring Boot Admin`是一个针对`spring-boot`的`actuator`接口进行UI美化封装的监控工具。它可以：在列表中浏览所有被监控spring-boot项目的基本信息，详细的Health信息、内存信息、JVM信息、垃圾回收信息、各种配置信息（比如数据源、缓存列表和命中率）等，还可以直接修改`logger`的`level`。

## 什么是SpringBoot监控中心

针对微服务器监控、服务器内存变化（堆内存，线程，日志管理等）、检测服务配置连接池是否可用（模拟访问、懒加载）、统计现有Bean（通过Spring容器）、Http接口（@RequestMapping）的一系列数据管理。Actuator监控应用只通过JSON形式返回数据统计结果，没有UI界面处理；AdminUI则内置Actuator服务监控，并对返回JSON数据进行图形化处理展示。

## 为什么要用SpringBoot监控中心

Actuator是SpringBoot的一个附加功能，可以帮助应用程序在生产环境运行时的监控和管理。可以使用HTTP的各个请求路径来监管、审计、收集引用的运行情况，特别对于微服务管理十分有意义；

AdminUI内置了Actuator服务，是对监控服务的图形化界面补充；

## 什么是SpringBoot-Admin

 Spring Boot Admin是一个开源社区项目，用于管理和监控SpringBoot应用程序。 应用程序作为Spring Boot Admin Client向为Spring Boot Admin Server注册（通过HTTP）或使用SpringCloud注册中心（例如Eureka，Consul，Zookeeper）发现。 UI是的AngularJs应用程序，展示Spring Boot Admin Client的Actuator端点上的一些监控。



# Spring-Boot-Admin常用功能

- 显示健康状况
- 显示详细信息，例如：JVM和内存指标、micrometer.io指标、数据源指标、缓存指标等
- 显示构建信息编号
- 关注并下载日志文件
- 查看jvm系统和环境属性
- 查看Spring Boot配置属性
- 支持Spring Cloud的postable / env-和/ refresh-endpoint
- 轻松的日志级管理
- 与JMX-beans交互
- 查看线程转储
- 查看http跟踪
- 查看auditevents
- 查看http-endpoints
- 查看计划任务
- 查看和删除活动会话（使用spring-session）
- 查看Flyway / Liquibase数据库迁移
- 下载heapdump
- 状态变更通知（通过电子邮件，Slack，Hipchat等）
- 状态更改的事件日志（非持久性）


# 结合Eureka注册中心实践
TODO  待完善

# 参考
- [https://blog.csdn.net/weixin_34067049/article/details/88677682](https://blog.csdn.net/weixin_34067049/article/details/88677682)

- [https://blog.csdn.net/hubo_88/article/details/80671192](https://blog.csdn.net/hubo_88/article/details/80671192)

- [https://blog.csdn.net/u011976388/article/details/85395130](https://blog.csdn.net/u011976388/article/details/85395130)

- [https://blog.csdn.net/typ1805/article/details/86289199](https://blog.csdn.net/typ1805/article/details/86289199)

- 监控中心内容介绍：[https://blog.csdn.net/sunhuansheng/article/details/84939446](https://blog.csdn.net/sunhuansheng/article/details/84939446)

- 邮件通知预警：[https://blog.csdn.net/sunhuansheng/article/details/84935302](https://blog.csdn.net/sunhuansheng/article/details/84935302)

- 自定义邮件预警内容：[https://my.oschina.net/xiedeshou/blog/2051625](https://my.oschina.net/xiedeshou/blog/2051625)

- 自定义UI参考：https://blog.csdn.net/weixin_43439494/article/details/85321941

  
