package com.nacos.demo.service.Impl;

import com.api.TService;
import org.apache.dubbo.config.annotation.Service;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/26
 **/

@Service(interfaceClass = TService.class)
public class TestService implements TService {
    @Override
    public String test() {
        return "ok";
    }
}
