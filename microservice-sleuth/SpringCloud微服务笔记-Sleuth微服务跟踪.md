##  为什么要实现微服务跟踪

分布式计算的八大误区(PeterDeutsch)：

| 网络可靠性       | 延迟为零         |
| :--------------- | ---------------- |
| 带宽无线         | 网络绝对安全     |
| 网络拓扑不会改变 | 必须有一名管理员 |
| 传输成本为零     | 网络同质化       |

简单分析：分布式服务中网络问题是一个突出的问题，网络常常很脆弱，同时网络资源也是有限的。

微服务之间是通过网络进行通讯的。如果能够跟踪每一个请求，了解请求都经过了哪些微服务(从而了解信息是如何在微服务之间流动的)、请求耗费时间、网络延迟、业务逻辑耗费时间等指标，那么就能更好的分析系统瓶颈、解决系统问题。因此微服务跟踪很有必要。

## Spring Cloud Sleuth简介

Spring cloud sleuth为Spring Cloud 提供了分布式跟踪的解决方案，它大量的借用Google、Twitter ZipKin和Apache HTrace的设计

## 术语简介

- Span(跨度)


工作的基本单位 例如，发送RPC是一个新的跨度，以及向RPC发送响应。Span由跨度的唯一64位ID标识，跨度是其中一部分的跟踪的另一个64位ID。跨度还具有其他数据，例如描述，时间戳记事件，键值注释（标签），导致它们的跨度的ID以及进程ID（通常是IP地址）。

跨距开始和停止，他们跟踪他们的时间信息。创建跨度后，必须在将来的某个时刻停止。

> 启动跟踪的初始范围称为`root span`。该跨度的跨度id的值等于跟踪ID

- trace(跟踪)


一组spans形成树状结构。例如，如果您正在运行分布式大数据存储，则可能会由put请求形成跟踪。

- annotation(标注):   用于及时记录事件的存在。用于定义请求的开始和停止的一些核心注释是
  - CS(Client Sent客户端发送): 客户端已经发出请求。此注释描绘了跨度的开始。
  - SR(Server Received服务器端接收数据):服务器端得到请求，并将开始处理它。如果从此时间戳中减去cs时间戳，则会收到网络延迟。
  - SS(Server Sent服务器端发送):在完成请求处理后（响应发送回客户端时）注释。如果从此时间戳中减去sr时间戳，则会收到服务器端处理请求所需的时间。
  - CR(Client Received客户端接收):表示跨度的结束。客户端已成功接收到服务器端的响应。如果从此时间戳中减去cs时间戳，则会收到客户端从服务器接收响应所需的整个时间。


## 整合sleuth微服务跟踪

- 创建工程

复制项目microservice-provider-user，将ArtifactId修改为microservice-provider-user-trace

复制项目microservice-consume-movie，将ArtifactId修改为microservice-consume-movie-trace

- 添加依赖

在其他依赖的基础上增加sleuth的依赖

```xml

	<!--微服务跟踪-->
	<dependency>
	    <groupId>org.springframework.cloud</groupId>
	    <artifactId>spring-cloud-starter-sleuth</artifactId>
	</dependency>

```

- 修改配置文件

```

	logging.level.root = info
	logging.level.org.springframework.web.servlet.DispatcherServlet=debug

```


- 启动测试

  - 启动服务注册组件：microservice-discovery-eureka
  - 启动服务提供者：microservice-provider-user-trace
  - 启动服务消费者：microservice-consume-movie-trace
  - 访问：http://127.0.0.1:9010/getUserById/23
  - 访问：http://127.0.0.1:9200/getUserInfo/25

  ```

	  2018-04-05 18:10:13.706 DEBUG [microservice-provider-user-trace,126e06392d62e4b0,0ae5a6dd363d5c15,false] 23560 --- [io-9010-exec-10] o.s.web.servlet.DispatcherServlet        : DispatcherServlet with name 'dispatcherServlet' processing GET request for [/getUserById/25]
	  2018-04-05 18:10:13.712 DEBUG [microservice-provider-user-trace,126e06392d62e4b0,0ae5a6dd363d5c15,false] 23560 --- [io-9010-exec-10] o.s.web.servlet.DispatcherServlet        : Last-Modified value for [/getUserById/25] is: -1
	  2018-04-05 18:10:13.714 DEBUG [microservice-provider-user-trace,126e06392d62e4b0,0ae5a6dd363d5c15,false] 23560 --- [io-9010-exec-10] o.s.web.servlet.DispatcherServlet        : Null ModelAndView returned to DispatcherServlet with name 'dispatcherServlet': assuming HandlerAdapter completed request handling
	  2018-04-05 18:10:13.715 DEBUG [microservice-provider-user-trace,126e06392d62e4b0,0ae5a6dd363d5c15,false] 23560 --- [io-9010-exec-10] o.s.web.servlet.DispatcherServlet        : Successfully completed request


  ```

```	

	2018-04-05 18:10:13.688 DEBUG [microservice-consume-movie-trace,126e06392d62e4b0,126e06392d62e4b0,false] 29728 --- [nio-9200-exec-6] o.s.web.servlet.DispatcherServlet        : DispatcherServlet with name 'dispatcherServlet' processing GET request for [/getUserInfo/25]
	2018-04-05 18:10:13.696 DEBUG [microservice-consume-movie-trace,126e06392d62e4b0,126e06392d62e4b0,false] 29728 --- [nio-9200-exec-6] o.s.web.servlet.DispatcherServlet        : Last-Modified value for [/getUserInfo/25] is: -1
	2018-04-05 18:10:13.719 DEBUG [microservice-consume-movie-trace,126e06392d62e4b0,126e06392d62e4b0,false] 29728 --- [nio-9200-exec-6] o.s.web.servlet.DispatcherServlet        : Null ModelAndView returned to DispatcherServlet with name 'dispatcherServlet': assuming HandlerAdapter completed request handling
	2018-04-05 18:10:13.719 DEBUG [microservice-consume-movie-trace,126e06392d62e4b0,126e06392d62e4b0,false] 29728 --- [nio-9200-exec-6] o.s.web.servlet.DispatcherServlet        : Successfully completed request
	2018-04-05 18:11:19.322  INFO [microservice-consume-movie-trace,,,] 29728 --- [trap-executor-0] c.n.d.s.r.aws.ConfigClusterResolver      : Resolving eureka endpoints via configuration


```

