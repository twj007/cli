package com.config.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/23
 **/
@Slf4j
@RestController
@RefreshScope
public class TestController {

    @Value("${app.title:}")
    private String title;

    @Value("${jdbc.type:}")
    private String type;

    @Value("${log.level:}")
    private String level;

    @GetMapping("/test")
    public String hello() {
        return "title:" + title + ", type:" + type + ", level:" + level;
    }

}
