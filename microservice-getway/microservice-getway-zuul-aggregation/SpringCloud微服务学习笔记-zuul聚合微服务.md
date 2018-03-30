# Zuul简介 

Zuul是Netflix开源的微服务网关，他可以和Eureka,Ribbon,Hystrix等组件配合使用。Zuul组件的核心是一系列的过滤器，这些过滤器可以完成以下功能：

- 身份认证和安全: 识别每一个资源的验证要求，并拒绝那些不符的请求
- 审查与监控：
- 动态路由：动态将请求路由到不同后端集群
- 压力测试：逐渐增加指向集群的流量，以了解性能
- 负载分配：为每一种负载类型分配对应容量，并弃用超出限定值的请求
- 静态响应处理：边缘位置进行响应，避免转发到内部集群
- 多区域弹性：跨域AWS Region进行请求路由，旨在实现ELB(ElasticLoad Balancing)使用多样化，以及让系统的边缘更贴近系统的使用者。
	
Spring Cloud对Zuul进行了整合和增强。目前，Zuul使用的默认是Apache的HTTP Client，也可以使用Rest Client，可以设置ribbon.restclient.enabled=true.

# 使用Zuul聚合微服务

许多场景下，外部请求需要查询Zuul后端多个微服务，比如：一个电影售票手机App，在购买订单也上，既需要查询“电影微服务”，有需要查询“用户微服务”，如果让手机端去请求，则需要多次请求，这样网络开销，流量耗费，耗费时长都无法令我们满意，对于这种场景可以使用Zuul聚合微服务请求-----手机APP只需要发送一个请求给Zuul，由Zuul请求用户微服务以及电影微服务，并组织数据发送给手机APP.


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

----------
	@SpringBootApplication
	@EnableZuulProxy   //开启Zuul网关功能
	public class ZuulApplication {
	
	    @LoadBalanced
	    @Bean
	    public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
	
	    public static void main(String[] args) {
	        SpringApplication.run(ZuulApplication.class, args);
	    }
	}



## 编写配置文件
从配置文件中可知，此处仅仅是添加了Zuul的依赖，并将Zuul注册到Eureka Server上。

----------

	# 应用启动的端口号
	server.port=8060
	#应用名称
	spring.application.name=microservice-getway-zuul-aggregation
	
	#Eureka的相关配置
	#如果可以在配置时确定主机名（否则将从操作系统原语中猜出）
	#eureka.instance.hostname=microservice-getway-zuul
	#表示将自己的IP注册到Eureka Server。如果不配置会将操作系统的hostname到Eureka Server.
	eureka.instance.prefer-ip-address=true
	# Eureka服务器地址也就是注册中心的地址 包含认证账号和密码
	eureka.client.serviceUrl.defaultZone=http://admin:admin123@localhost:8761/eureka

## 创建实体类

----------

	@Setter
	@Getter
	@ToString
	public class User {
	
	    private Integer id;
	    private String name;
	    private Integer age;
	}


## 创建Services
调用对应的微服务

----------

	@Service
	public class AggregationService {
	
	    @Autowired
	    private RestTemplate restTemplate;
	
	    @HystrixCommand(fallbackMethod = "fallback")
	    public Observable<User> getUserById(Integer id) {
	        // 创建一个被观察者
	        return Observable.create(observer -> {
	            // 请求用户微服务的/{id}端点
	            User user = restTemplate.getForObject("http://microservice-provider-user/getUserById/{id}", User.class, id);
	            observer.onNext(user);
	            observer.onCompleted();
	        });
	    }
	
	    @HystrixCommand(fallbackMethod = "fallback")
	    public Observable<User> getMovieUserByUserId(Integer id) {
	        return Observable.create(observer -> {
	            // 请求电影微服务的/user/{id}端点
	            User movieUser = restTemplate.getForObject("http://microservice-consume-movie/getUserInfo/{id}", User.class, id);
	            observer.onNext(movieUser);
	            observer.onCompleted();
	        });
	    }
	
	    /**
	     * 回退方法
	     *
	     * @param id
	     * @return
	     */
	    public User fallback(Integer id) {
	        User user = new User();
	        user.setId(900);
	        return user;
	    }
	
	}


## 创建Controller

----------

	@RestController
	public class AggregationController {
	
	    public static final Logger LOGGER = LoggerFactory.getLogger(ZuulApplication.class);
	
	    @Autowired
	    private AggregationService aggregationService;
	
	    @GetMapping("/aggregate/{id}")
	    public DeferredResult<HashMap<String, User>> aggregate(@PathVariable Integer id) {
	        Observable<HashMap<String, User>> result = this.aggregateObservable(id);
	        return this.toDeferredResult(result);
	    }
	
	    public Observable<HashMap<String, User>> aggregateObservable(Integer id) {
	        // 合并两个或者多个Observables发射出的数据项，根据指定的函数变换它们
	        return Observable.zip(
	                this.aggregationService.getUserById(id),
	                this.aggregationService.getMovieUserByUserId(id),
	                (user, movieUser) -> {
	                    HashMap<String, User> map = Maps.newHashMap();
	                    map.put("user", user);
	                    map.put("movieUser", movieUser);
	                    return map;
	                }
	        );
	    }
	
	    public DeferredResult<HashMap<String, User>> toDeferredResult(Observable<HashMap<String, User>> details) {
	        DeferredResult<HashMap<String, User>> result = new DeferredResult<>();
	        // 订阅
	        details.subscribe(new Observer<HashMap<String, User>>() {
	            @Override
	            public void onCompleted() {
	                LOGGER.info("完成...");
	            }
	
	            @Override
	            public void onError(Throwable throwable) {
	                LOGGER.error("发生错误...", throwable);
	            }
	
	            @Override
	            public void onNext(HashMap<String, User> movieDetails) {
	                result.setResult(movieDetails);
	            }
	        });
	        return result;
	    }
	}

## 测试：路由规则


- 启动服务注册中心项目：microservice-discovery-eureka
- 启动服务提供者项目：microservice-provider-user
- 启动服务消费者项目：microservice-consume-movie
- 启动路由网关聚合服务项目： microservice-getway-zuul-aggregation
- 访问：http://127.0.0.1:8761/  检查服务是否启动成功

![](https://i.imgur.com/VC27aEJ.png)


- 访问：http://127.0.0.1:8060/aggregate/80  可查看到聚合数据


![](https://i.imgur.com/80x8XvW.png)