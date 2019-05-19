package com.taowd.feign;

import com.taowd.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Taowd
 * @date 2018/3/14 - 15:11
 * @Description
 */
//Value = 此接口对应的微服务名称
@FeignClient(value = "microservice-provider-user", fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient {

    /**
     * 获取一个字符串
     *
     * @return
     */
    // 两个坑：1. @GetMapping不支持   jquery-easyui. @PathVariable得设置value
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello();

    /**
     * 获取用户对象
     *
     * @return
     */
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public User getUserInfo();


}
