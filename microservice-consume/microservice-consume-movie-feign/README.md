> 本项目记录整合Feign声明式Http客户端功能

### 要点

- Feign中已经包含了Ribbon  当前这一版本的代码已经实现负载均衡，启动注册中心microservice-discovery-eureka和多个服务的服务提供者
microservice-provider-user  以及当前项目，访问：http://127.0.0.1:9101/say2  可以发现端口号在多个服务提供者直接切换，
默认Ribbon的负载均衡策略是简单轮询负载均衡

- 添加依赖
`
        <!--声明式的Http客户端调用-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
        </dependency>
`

- 启动类上添加注解
- 创建接口类(对应某个微服务下的方法)

注意： 方法声明是返回值和参数必须和对应微服务中的方法一致

在@FeignClient注释中，String值（以上“存储”）是一个任意的客户端名称，用于创建Ribbon负载平衡器（有关Ribbon支持的详细信息，请参阅下文））。您还可以使用url属性（绝对值或只是主机名）指定URL。应用程序上下文中的bean的名称是该接口的完全限定名称。要指定您自己的别名值，您可以使用@FeignClient注释的qualifier值。


- Controller中进行调用