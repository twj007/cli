package com.nacos.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/22
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class NacosApp {

    public static void main(String[] args) {
        SpringApplication.run(NacosApp.class, args);
    }
    @Slf4j
    @RestController
    static class TestController {
        @GetMapping("/hello")
        public String hello(@RequestParam String name) {
            log.info("invoked name = " + name);
            return "hello " + name;
        }
    }

}
