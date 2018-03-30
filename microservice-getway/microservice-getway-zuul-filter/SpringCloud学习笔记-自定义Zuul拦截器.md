# Zuul过滤器机制

zuul的核心是一系列的filters, 其作用可以类比Servlet框架的Filter，或者AOP。

zuul把Request route到 用户处理逻辑 的过程中，这些filter参与一些过滤处理，比如Authentication，Load Shedding等。

Spring Cloud对Zuul进行了整合和增强。目前，Zuul使用的默认是Apache的HTTP Client，也可以使用Rest Client，可以设置ribbon.restclient.enabled=true.

![](https://images2015.cnblogs.com/blog/486074/201702/486074-20170220185335288-1703224333.png)

Zuul提供了一个框架，可以对过滤器进行动态的加载，编译，运行。

Zuul的过滤器之间没有直接的相互通信，他们之间通过一个RequestContext的静态类来进行数据传递的。RequestContext类中有ThreadLocal变量来记录每个Request所需要传递的数据。

Zuul的过滤器是由Groovy写成，这些过滤器文件被放在Zuul Server上的特定目录下面，Zuul会定期轮询这些目录，修改过的过滤器会动态的加载到Zuul Server中以便过滤请求使用。

# 过滤器类型：

Zuul大部分功能都是通过过滤器来实现的。Zuul中定义了四种标准过滤器类型，这些过滤器类型对应于请求的典型生命周期。

- PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
- ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
- POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
- ERROR：在其他阶段发生错误时执行该过滤器。

# 内置的特殊过滤器

zuul还提供了一类特殊的过滤器，分别为：StaticResponseFilter和SurgicalDebugFilter

StaticResponseFilter：StaticResponseFilter允许从Zuul本身生成响应，而不是将请求转发到源。

SurgicalDebugFilter：SurgicalDebugFilter允许将特定请求路由到分隔的调试集群或主机。

自定义的过滤器

除了默认的过滤器类型，Zuul还允许我们创建自定义的过滤器类型。

例如，我们可以定制一种STATIC类型的过滤器，直接在Zuul中生成响应，而不将请求转发到后端的微服务。

# 过滤器的生命周期

Zuul请求的生命周期如图，该图详细描述了各种类型的过滤器的执行顺序。

![](https://images2015.cnblogs.com/blog/1099841/201706/1099841-20170630111344414-1260445909.png)

# 编写Zuul微服务网关

## 创建工程，添加依赖

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

## 创建自定义过滤器

继承ZuulFilter类，实现：filterType，filterOrder，shouldFilter，run方法
PreRequestLogFilter:功能是打印请求日志

	package com.taowd.filter;
	
	import com.netflix.zuul.ZuulFilter;
	import com.netflix.zuul.context.RequestContext;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	
	import javax.servlet.http.HttpServletRequest;
	
	/**
	 * @author Taowd
	 * @date 2018/3/27 - 14:33
	 * @Description
	 */
	public class PreRequestLogFilter extends ZuulFilter {
	
	    private static final Logger logger = LoggerFactory.getLogger(PreRequestLogFilter.class);
	
	    @Override
	    public String filterType() {
	        return "pre";
	    }
	
	    @Override
	    public int filterOrder() {
	        return 1;
	    }
	
	    @Override
	    public boolean shouldFilter() {
	        return true;
	    }
	
	    @Override
	    public Object run() {
	        RequestContext ctx = RequestContext.getCurrentContext();
	        HttpServletRequest request = ctx.getRequest();
	        PreRequestLogFilter.logger.info(String.format("send %s request to %s", request.getMethod(), request.getRequestURI().toString()));
	
	        return null;
	    }
	}



## 测试

- 启动服务注册中心项目：microservice-discovery-eureka
- 启动服务提供者项目：microservice-provider-user
- 启动路由网关项目： microservice-getway-zuul-filter
- 访问：http://127.0.0.1:8761/  检查服务是否启动成功
- 访问：http://127.0.0.1:8040/microservice-provider-user/hello   检查服务是否成功

检查microservice-getway-zuul-filter项目控制台，打印请求路径



# 禁用Zuul拦截器功能

----------

	#禁用Zuul拦截器
	zuul.PreRequestLogFilter.pre.disable=true