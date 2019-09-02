package com.api.user;

import com.common.pojo.ShiroUser;
import com.common.pojo.user.Menu;
import com.common.pojo.user.UserInfo;

import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
public interface UserService {

    ShiroUser login(ShiroUser user);

    ShiroUser register(ShiroUser shiroUser);

    UserInfo getUserInfo(ShiroUser user);

    UserInfo updateUserInfo(UserInfo userInfo);


}
