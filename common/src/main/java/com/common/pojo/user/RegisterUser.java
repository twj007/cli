package com.common.pojo.user;

import com.common.pojo.ShiroUser;
import lombok.*;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Getter
@Setter
public class RegisterUser extends ShiroUser {

    public RegisterUser() {
    }

    public RegisterUser(String username, String password) {
        super(username, password);
    }


}
