package com.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/27
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class FrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrontApplication.class, args);
    }
}
