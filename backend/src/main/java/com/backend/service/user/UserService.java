package com.backend.service.user;

import com.backend.dao.user.IUserDao;
import com.common.annotation.DataSource;
import com.common.pojo.ShiroUser;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Service(interfaceClass = com.api.user.UserService.class)
public class UserService implements com.api.user.UserService {

    @Autowired
    private IUserDao userDao;

    @Override
    @DataSource
    public ShiroUser login(ShiroUser user) {
        return userDao.login(user);
    }

    @Override
    @DataSource
    public ShiroUser register(ShiroUser shiroUser) {
        userDao.register(shiroUser);
        return shiroUser;
    }
}
