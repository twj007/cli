package com.oauth2.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/03
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class Oauth2Server {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2Server.class, args);
    }
}
