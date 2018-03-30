> 本项目记录使用消息中间件RibbitMQ收集Hystrix Command的监控数据

#Hystrix简介
 一些场景时，可以借助消息中间件完成数据收集功能，各个服务将Hystrix Command的监控数据发送至消息中间件，
 Turbine消费消息中间件中的数据

# 功能实现

1、添加依赖
   
	<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-hystrix</artifactId>
	</dependency>

2、启动类添加注解

	@SpringBootApplication
	@EnableCircuitBreaker
	public class Application {
	public static void main(String[] args) {
	        new SpringApplicationBuilder(Application.class).web(true).run(args);
	    }
	}

3、对应接口上添加注解
在FeignClient注解中添加fallback属性，在服务熔断的时候返回fallback类中的内容。
	
    @FeignClient(value = "microservice-provider-user",fallback = UserFeignClientFallBack.class)
    public interface UserFeignClient {
    .....省略
    }
4、创建UserFeignClient对应的返回类UserFeignClientFallBack
   
注意：UserFeignClientFallBack类必须实现UserFeignClient接口中的方法，方法体内即发生故障之后返回的内容
	
	@Component
    public class UserFeignClientFallBack implements UserFeignClient {
        @Override
        public String sayHello() {
            return "这里是sayHello方法调用时返回的 FallBack方法";
        }
    
        @Override
        public User getUserInfo() {
    
            User user = new User();
            user.setId(90000);
            user.setName("李fallback");
            user.setAge(24);
    
            return user;
        }
    }

# 测试结果

- 启动Eureka注册中心：microservice-discovery-eureka
- 启动服务提供者：microservice-provider-user
- 启动服务消费者： microservice-consume-movie-feign-hystrix  其中包括Hystrix熔断机制的方法回退
- 访问microservice-consume-movie-feign-hystrix中的方法，正常
- 关闭服务提供者：microservice-provider-user
- 访问microservice-consume-movie-feign-hystrix中的方法，返回编写的回退方法内的内容

![](https://i.imgur.com/6Re7sz4.png)