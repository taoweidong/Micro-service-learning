package com.taowd.controller;

import com.alibaba.fastjson.JSON;
import com.taowd.entity.EurekaEntity;
import com.taowd.entity.User;
import com.taowd.feign.UserFeignClient;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Taowd
 * @date 2018/3/12 - 17:12
 * @Description
 */
@RestController
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/say")
    public String say() {
        return "Hello  这里是本地提供的服务，没有进行调用服务提供者！";
    }

    @GetMapping("/say2")
    public String say2() {
        // 使用最基本的restTemplate进行服务间通讯
        return userFeignClient.sayHello();
    }

    @GetMapping("/getUserInfo")
    public User getUser() {
        return userFeignClient.getUserInfo();
    }

    /**
     * 注册中心服务列表获取
     * 
     * @return
     */
    @GetMapping("/getEurekaService")
    public User getEurekaService() {

        List<String> services = discoveryClient.getServices();
        services.stream().forEach(x -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(x);
            instances.stream().forEach(item -> {
                System.out.println(JSON.toJSONString(item));

                EurekaEntity eurekaEntity = JSON.parseObject(JSON.toJSONString(item), EurekaEntity.class);

                System.out.println("实体转换：" + JSON.toJSONString(eurekaEntity));

            });
        });

        return userFeignClient.getUserInfo();
    }
}
