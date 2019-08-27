package com.backend.service;

import com.api.TService;
import com.backend.dao.PmsProductMapper;
import com.common.annotation.DataSource;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/27
 **/
@Service(interfaceClass = TService.class)
public class TestService implements TService {

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Override
    @DataSource
    public String test() {
        pmsProductMapper.recod();
        return "success";
    }
}
