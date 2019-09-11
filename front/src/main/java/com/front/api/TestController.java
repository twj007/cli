package com.front.api;

import com.api.TService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/27
 **/
@RestController
public class TestController {

    @Reference(interfaceClass = TService.class, lazy = true, check = false)
    TService testService;

    @GetMapping("/test")
    @PreAuthorize("hasAuthority('read')")
    public String test(){
        return testService.test();
    }
}
