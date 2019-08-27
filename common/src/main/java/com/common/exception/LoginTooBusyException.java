package com.common.exception;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 **/
public class LoginTooBusyException extends RuntimeException {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LoginTooBusyException(String message, String username) {
        super(message);
        this.username = username;
    }

    public LoginTooBusyException(String message) {
        super(message);
    }

    public LoginTooBusyException() {
    }
}
