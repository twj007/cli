package com.api.user;

import com.common.pojo.SysUser;
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

    SysUser login(SysUser user);

    SysUser register(SysUser shiroUser);

    UserInfo getUserInfo(SysUser user);

    UserInfo updateUserInfo(UserInfo userInfo);


    SysUser getById(Long id);
}
