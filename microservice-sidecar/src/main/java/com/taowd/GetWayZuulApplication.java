package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.sidecar.EnableSidecar;

@SpringBootApplication
@EnableSidecar   //开启Sidecar功能
public class GetWayZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(GetWayZuulApplication.class, args);
    }
}
