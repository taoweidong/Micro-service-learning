package com.taowd.controller;

import com.taowd.entity.User;
import com.taowd.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/say")
    public String say() {
        return "Hello  这里是本地提供的服务，没有进行调用服务提供者！";
    }

    @GetMapping("/say2")
    public String say2() {
        //使用最基本的restTemplate进行服务间通讯
        return userFeignClient.sayHello();
    }

    @GetMapping("/getUserInfo")
    public User getUser() {
        return userFeignClient.getUserInfo();
    }
}
