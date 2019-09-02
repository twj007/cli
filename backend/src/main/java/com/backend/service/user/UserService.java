package com.backend.service.user;

import com.backend.dao.user.IUserDao;
import com.common.annotation.DataSource;
import com.common.pojo.ShiroUser;
import com.common.pojo.user.UserInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Service(interfaceClass = com.api.user.UserService.class, filter = "RpcFilter", timeout = 10000)
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
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShiroUser register(ShiroUser shiroUser) {
        userDao.register(shiroUser);
        return shiroUser;
    }

    @Override
    public UserInfo getUserInfo(ShiroUser user) {
        //UserInfo info = userDao.getUserInfo(user);
        UserInfo info = null;
        return info;
    }

    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {
        return null;
    }

    @DataSource(type = "slaver")
    @Transactional(readOnly = true)
    public Long getRecord(Long id){
        return userDao.getRecord(id);
    }
}
