package com.front.component;

import com.common.exception.KickOutException;
import com.common.exception.LoginTooBusyException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/16
 **/
@RestControllerAdvice
public class ErrorResolver {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResolver.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionhandler(Exception e){
        e.printStackTrace();
        if(e.getCause() instanceof KickOutException){
            logger.warn("【login】: 用户{} 已被顶出", ((KickOutException)e.getCause()).getSessionId());
            return ResponseEntity.badRequest().body("你已被顶出，请重新登陆");
        }
        return ResponseEntity.badRequest().body("系统异常");
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationException(AuthenticationException e){
        if(e.getCause() instanceof LoginTooBusyException){
            logger.warn("【login】: 频繁尝试登陆失败, 账号{}已锁", ((LoginTooBusyException)e.getCause()).getUsername());
            return ResponseEntity.badRequest().body("失败次数过多，账号已锁定，请五分钟后重试");
        }

        return ResponseEntity.badRequest().body("登陆失败");
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity authorizationException(AuthorizationException e){
        return ResponseEntity.badRequest().body("未授权或登陆超时"+e.getMessage());
    }

    @ExceptionHandler(LoginTooBusyException.class)
    public ResponseEntity unexpectedException(LoginTooBusyException e){
        return ResponseEntity.badRequest().body("密码出错次数过多，请在5分钟后重试");
    }
}
