> 本项目记录在Feign和Ribbon的基础上整合Hystrix

#Hystrix简介

Hystrix 能使你的系统在出现依赖服务失效的时候，通过隔离系统所依赖的服务，防止服务级联失败，同时提供失败回退机制，更优雅地应对失效，并使你的系统能更快地从异常中恢复。

注意： 这里的回退方法的添加是在Controller中指定方法上进行添加的

#Hystrix特性

## 断路器机制
断路器很好理解, 当Hystrix Command请求后端服务失败数量超过一定比例(默认50%), 断路器会切换到开路状态(Open). 这时所有请求会直接失败而不会发送到后端服务. 断路器保持在开路状态一段时间后(默认5秒), 自动切换到半开路状态(HALF-OPEN). 这时会判断下一次请求的返回情况, 如果请求成功, 断路器切回闭路状态(CLOSED), 否则重新切换到开路状态(OPEN). Hystrix的断路器就像我们家庭电路中的保险丝, 一旦后端服务不可用, 断路器会直接切断请求链, 避免发送大量无效请求影响系统吞吐量, 并且断路器有自我检测并恢复的能力.

## Fallback
Fallback相当于是降级操作. 对于查询操作, 我们可以实现一个fallback方法, 当请求后端服务出现异常的时候, 可以使用fallback方法返回的值. fallback方法的返回值一般是设置的默认值或者来自缓存.

## 资源隔离
在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线程池. 例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池. 这样做的主要优点是运行环境被隔离开了. 这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗尽时, 不会对系统的其他服务造成影响. 但是带来的代价就是维护多个线程池会对系统带来额外的性能开销. 如果是对性能有严格要求而且确信自己调用服务的客户端代码不会出问题的话, 可以使用Hystrix的信号模式(Semaphores)来隔离资源.

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
在HelloRemote类添加指定fallback类，在服务熔断的时候返回fallback类中的内容。
	
	@HystrixCommand(fallbackMethod="findByIdFallback")
	@GetMapping("/movie/{id}")
	public User findById(@PathVariableLong id){
		return this.restTemplate.getForObject("http://microservice-provider-user/simple/"+id,User.class);
	}

4、编写对应的回调事件
	
	/**
	*通讯故障时执行的回退方法
	* 注意：此方法和对应的接口参数和返回值类型必须相同
	*@paramid
	*@return
	*/
	public User findByIdFallback(Longid){
		User user=new User();
		user.setId(0L);
		return user;
	}

# 测试结果

- 启动Eureka注册中心：microservice-discovery-eureka
- 启动服务提供者：microservice-provider-user
- 启动服务消费者： microservice-consume-movie-feign-hystrix  其中包括Hystrix熔断机制的方法回退

![](https://i.imgur.com/6Re7sz4.png)