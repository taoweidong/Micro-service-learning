package com.taowd.controller;

import com.alibaba.fastjson.JSON;
import com.taowd.entity.Token;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taowd.entity.User;

/**
 * @author Taoweidong
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Value("${server.port}")
    private String port;

    /**
     * 登录功能--鉴权.
     * 
     * @return 用户信息
     */
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> queryParam) {
        System.out.println(JSON.toJSONString(queryParam));

        Map<String, Object> resultMap = new HashMap<>(2);
        resultMap.put("code", 20000);
        Map<String, Object> data = new HashMap<>(2);
        data.put("token", "Taoweidong-token");
        resultMap.put("data", data);

        return resultMap;
    }

    /**
     * 登录功能.
     *
     * @return 用户信息
     */
    @GetMapping("/info")
    public Token info(String token) {

        List<String> roles = new ArrayList<>();
        roles.add("admin");
        Token token1 = Token.builder().name("Taoweidong")
            .avatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif").introduction(token)
            .roles(roles).build();

        return token1;
    }

    @GetMapping("/hello")
    public String sayHello() {

        return "Hello 这里是用户服务提供者。。。 当前端口号为：" + port;
    }

    @GetMapping("/getUser")
    public User getUser() {

        User user = new User();
        user.setId(88999);
        user.setUsername("李三思");
        user.setAge(34);
        return user;
    }

    @GetMapping("/getUserById/{id}")
    public User getUserById(@PathVariable Integer id) {

        User user = new User();
        user.setId(90000);
        user.setUsername("李三");
        user.setAge(id);
        return user;
    }

}
