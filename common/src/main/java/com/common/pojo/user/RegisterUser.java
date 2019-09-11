package com.common.pojo.user;

import com.common.pojo.SysUser;
import lombok.*;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Getter
@Setter
public class RegisterUser extends SysUser {

    public RegisterUser() {
    }

    public RegisterUser(String username, String password) {
        super(username, password);
    }


}
