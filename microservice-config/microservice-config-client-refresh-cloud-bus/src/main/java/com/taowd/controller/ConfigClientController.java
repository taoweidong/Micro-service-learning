package com.taowd.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Taowd
 * @date 2018/3/30 - 13:18
 * @Description
 */
@RestController
public class ConfigClientController {

    @Value("${profile}")
    private String profile;

    @GetMapping("/profile")
    public String hello() {

        return this.profile;
    }

}
