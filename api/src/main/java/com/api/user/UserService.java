package com.api.user;

import com.common.pojo.ShiroUser;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
public interface UserService {

    ShiroUser login(ShiroUser user);

    ShiroUser register(ShiroUser shiroUser);
}
