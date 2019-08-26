package com.nacos.demo.service.Impl;

import com.nacos.demo.service.ITestService;
import org.apache.dubbo.config.annotation.Service;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/26
 **/

@Service(interfaceClass = ITestService.class)
public class TestService implements ITestService {
    @Override
    public String test() {
        return "ok";
    }
}
