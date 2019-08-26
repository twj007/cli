package com.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/26
 **/
@SpringBootApplication
@RestController
public class SentinelApp {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApp.class, args);
    }

    @GetMapping("/hello")
    public String hello() {
        return "didispace.com";
    }
}
