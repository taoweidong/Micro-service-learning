# Eureka 简要介绍
Eureka 是 Netflix 开发的，一个基于 REST 服务的，服务注册与发现的组件

# Eureka集群高可用配置
一般生产的环境中，一定要确保服务的正常使用，不能出现服务宕机的情况；在微服务架构下这种要求更加重要，注册中心作为微服务架构中的重要一环，在设计之初就已经考虑到服务的单点问题；单节点的eureka服务很难保证服务的不间断，如果eureka服务宕机，则会导致整个项目应用都会出现问题，基于单点故障的问题，最好的解决方案就是采用eureka集群部署方式搭建eureka的高可用，再多个注册中心的模式下，多个eureka服务之间相关注册，同步注册数据，在一个eureka宕机的情况下，其他服务仍然可以提供服务注册和服务发现功能，从而保证注册中心的高可用。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191230224738627.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rhb3dlaWRvbmcx,size_16,color_FFFFFF,t_70)

# 搭建步骤
## POM依赖

```xml

  <dependencies>
    <!--添加Eureka server的jar包 -->
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <!--添加安全认证的jar包 -->
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-security</artifactId>
    </dependency>
    <!-- actuator监控信息完善 -->
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>

  </dependencies>

  <build>
    <plugins>
      <!--注意此处如果不配置这个打包插件的话，可能会导致spring-boot-starter-actuator /info获取空信息-->
      <!--参考：https://blog.csdn.net/weixin_34402408/article/details/86001236-->
      <plugin>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-maven-plugin</artifactId>
      </plugin>
    </plugins>
  </build>
```

## 启动类增加注解

```java
/**
 * Eureka服务注册中心
 * @author Taoweidong
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {
    /**
     * 启动类
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApp.class, args);
    }
}
```

## 增加配置文件application.properties

```properties
# 应用启动的端口号
server.port=8762
#激活配置文件，默认为master
spring.profiles.active=slave
#服务名称
spring.application.name=eureka-service

#安全认证相关配置
#开启安全认证
security.basic.enabled=false
#安全认证用户名
security.user.name=admin
#安全认证密码
security.user.password=admin123

```

## 增加主注册中心配置文件application-master.properties

```properties
#Eureka的相关配置
#应用的主机名称
eureka.instance.hostname=master
# 通过eureka.client.registerWithEureka：false和fetchRegistry：false来表明自己是一个eureka server.  这两个参数的默认值都是True，因此作为客户端是可不进行配置，使用默认值就可以了
#值为false意味着自身仅作为服务器，不作为客户端,进行集群式设置为true
eureka.client.register-with-eureka=false
# 值为false意味着无需注册自身
eureka.client.fetch-registry=false
#Eureka的自我保护机制，True表示开启，false表示关闭，默认为开启状态
eureka.server.enable-self-preservation=true
#清理间隔(单位毫秒)驱逐下线的服务，间隔是5秒，默认是60
eureka.server.eviction-interval-timer-in-ms=5000
#
# 指明了本应用的URL
eureka.client.serviceUrl.defaultZone=http://admin:admin123@${eureka.instance.hostname}:${server.port}/eureka/
```

## 增加从注册中心配置application-slave.properties

```properties
#Eureka的相关配置
#应用的主机名称
eureka.instance.hostname=slave
# 通过eureka.client.registerWithEureka：false和fetchRegistry：false来表明自己是一个eureka server.  这两个参数的默认值都是True，因此作为客户端是可不进行配置，使用默认值就可以了
#值为false意味着自身仅作为服务器，不作为客户端,进行集群式设置为true
eureka.client.register-with-eureka=false
# 值为false意味着无需注册自身
eureka.client.fetch-registry=false
#Eureka的自我保护机制，True表示开启，false表示关闭，默认为开启状态
eureka.server.enable-self-preservation=true
#清理间隔(单位毫秒)驱逐下线的服务，间隔是5秒，默认是60
eureka.server.eviction-interval-timer-in-ms=5000
#
# 指明了本应用的URL
eureka.client.serviceUrl.defaultZone=http://admin:admin123@${eureka.instance.hostname}:${server.port}/eureka/
```

## 修改host文件-windows

```cmd
# Copyright (c) 1993-2009 Microsoft Corp.
#
# This is a sample HOSTS file used by Microsoft TCP/IP for Windows.
#
# This file contains the mappings of IP addresses to host names. Each
# entry should be kept on an individual line. The IP address should
# be placed in the first column followed by the corresponding host name.
# The IP address and the host name should be separated by at least one
# space.
#
# Additionally, comments (such as these) may be inserted on individual
# lines or following the machine name denoted by a '#' symbol.
#
# For example:
#
#      102.54.94.97     rhino.acme.com          # source server
#       38.25.63.10     x.acme.com              # x client host

# localhost name resolution is handled within DNS itself.
#	127.0.0.1       localhost
#	::1             localhost
	127.0.0.1       master
	127.0.0.1       slave
```

## 修改host文件-linux
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020010523344845.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200105233431744.png)

## 结构图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191230225952251.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rhb3dlaWRvbmcx,size_16,color_FFFFFF,t_70)
## 使用两个端口启动master和slave
![在这里插入图片描述](https://img-blog.csdnimg.cn/20191230230041122.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rhb3dlaWRvbmcx,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20191230230059679.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3Rhb3dlaWRvbmcx,size_16,color_FFFFFF,t_70)
> 启动命令

```powershell
# 杀死注册中心进程
ps -ef | grep microservice-getway-0.0.1-SNAPSHOT.jar | grep -v grep | awk '{print $2}' | xargs kill -9

# 启动注册中心
nohup java -jar microservice-discovery-eureka-0.0.1-SNAPSHOT.jar --server.port=8761 --spring.profiles.active=master  > eureka-master.log 2>&1 &
nohup java -jar microservice-discovery-eureka-0.0.1-SNAPSHOT.jar --server.port=8762 --spring.profiles.active=slave  > eureka-slave.log 2>&1 &
```

# 参考
- [https://blog.csdn.net/qq_38455201/article/details/80898045](https://blog.csdn.net/qq_38455201/article/details/80898045)
- [https://blog.csdn.net/sofeware333/article/details/90049898](https://blog.csdn.net/sofeware333/article/details/90049898)
- [https://www.cnblogs.com/knowledgesea/p/11208000.html](https://www.cnblogs.com/knowledgesea/p/11208000.html)
- 详细配置说明：https://www.cnblogs.com/zyon/p/11023750.html
