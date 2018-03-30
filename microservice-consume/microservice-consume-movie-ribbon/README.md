> 本项目是在microservice-consume-movie子项目的基础之上进行增加Ribbon的功能，测试基本的RestTemplate使用Ribbon负载均衡的功能

# 2018年3月14日11:47:06  Ribbon基本使用

## 创建SpringBoot项目结构

## 添加Maven依赖

注意：因为这里使用了Eureka客户端，而Eureka的依赖中包含了Ribbon，因此不需要引入Ribbon的依赖

## 添加注释

@LoadBalanced 
  
## 测试

# 2018年3月14日11:46:59  Ribbon使用自定义配置

## 创建配置类

## 启动类增加注解配置，指定微服务的Ribbon自定义配置