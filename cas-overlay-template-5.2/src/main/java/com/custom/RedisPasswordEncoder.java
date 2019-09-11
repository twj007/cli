package com.custom;

import org.springframework.security.crypto.password.PasswordEncoder;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/09
 **/
public class RedisPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
