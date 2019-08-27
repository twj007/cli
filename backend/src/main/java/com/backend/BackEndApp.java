package com.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/27
 **/
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.backend.dao"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class BackEndApp {

    public static void main(String[] args) {
        SpringApplication.run(BackEndApp.class, args);
    }
}
