package com.taowd.controller;

import com.taowd.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Taowd
 * @date 2018/3/12 - 17:12
 * @Description
 */
@RestController
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 从application配置文件中获取服务提供者的Url地址，便于修改时统一修改
     */
    @Value("${user.userServicePath}")
    private String userServicePath;

    @GetMapping("/say")
    public String say() {
        return "Hello  这里是本地提供的服务，没有进行调用服务提供者！" + userServicePath;
    }

    /**
     * JVM服务使用非JVM服务
     * @return
     */
    @GetMapping("/say2")
    public String say2() {
        //使用最基本的restTemplate进行服务间通讯
        return restTemplate.getForObject("http://microservice-provider-user/hello", String.class);
    }


    @GetMapping("/node")
    public String NodeInfo() {
        //使用最基本的restTemplate进行服务间通讯
        return restTemplate.getForObject("http://microservice-sidecar/", String.class);
    }

    @GetMapping("/getUserInfo")
    public User getUser() {
        return restTemplate.getForObject(userServicePath + "getUser", User.class);
    }
}
