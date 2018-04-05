package com.taowd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class ZipKinServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipKinServerApplication.class, args);
    }
}
