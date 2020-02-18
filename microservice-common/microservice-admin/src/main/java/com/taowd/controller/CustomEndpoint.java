package com.taowd.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author Taoweidong
 */
@Endpoint(id = "custom")
public class CustomEndpoint {

  @ReadOperation
  public String invoke() {
    return JSON.toJSONString("Hello World! CustomEndpoint");
  }

}
