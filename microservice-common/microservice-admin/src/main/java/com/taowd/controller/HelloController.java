package com.taowd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Taoweidong
 */
@RestController("/hello")
public class HelloController {

  /**
   * 测试接口输出
   * 
   * @return
   */
  @GetMapping("/world")
  public String sayHelloWorld() {
    return "Hello World ------->this is Spring boot admin";
  }
}
