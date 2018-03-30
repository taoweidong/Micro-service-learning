package com.taowd.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(fallbackMethod = "getUserFallback")
    @GetMapping("/getUserInfo")
    public User getUser() {
        return userFeignClient.getUserInfo();
    }

    /**
     *   getUser方法在执行调用微服务时发生异常时会调用此方法的内容
     *   注意：此方法为回退方法，必须与发生异常的方法返回值保持一致，参数也保持一致
     * @return
     */
    public User getUserFallback() {
        User user = new User();
        user.setId(99999);
        user.setName("fallback");
        user.setAge(0);
        return user;
    }

}
