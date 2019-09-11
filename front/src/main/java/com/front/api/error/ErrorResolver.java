package com.front.api.error;

import com.common.exception.KickOutException;
import com.common.exception.LoginTooBusyException;
import org.apache.dubbo.remoting.RemotingException;
import org.apache.dubbo.rpc.RpcException;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authz.AuthorizationException;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/16
 **/
@RestControllerAdvice
public class ErrorResolver {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResolver.class);

    @GetMapping("/error")
    public ResponseEntity error(){
        return ResponseEntity.badRequest().body("unexpected error happened");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e){
        logger.error("【exception】: {}", e.fillInStackTrace());
        if(e.getCause() instanceof KickOutException){
            logger.warn("【login】: 用户{} 已被顶出", ((KickOutException)e.getCause()).getSessionId());
            return ResponseEntity.badRequest().body("你已被顶出，请重新登陆");
        }
        if(e instanceof BindException){
            logger.warn("【login】: {}", ((BindException)e).getAllErrors());
            return ResponseEntity.badRequest().body(((BindingResult)e).getAllErrors());
        }
        if(e instanceof  AccessDeniedException){
            logger.warn("【login】: {}, {}", ((AccessDeniedException)e).getMessage(), e.getStackTrace());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.badRequest().body("系统异常");
    }



//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity authenticationException(AuthenticationException e){
//        if(e.getCause() instanceof LoginTooBusyException){
//            logger.warn("【login】: 频繁尝试登陆失败, 账号{}已锁", ((LoginTooBusyException)e.getCause()).getUsername());
//            return ResponseEntity.badRequest().body("失败次数过多，账号已锁定，请五分钟后重试");
//        }
//
//        return ResponseEntity.badRequest().body("登陆失败");
//    }
//
//    @ExceptionHandler(AuthorizationException.class)
//    public ResponseEntity authorizationException(AuthorizationException e){
//        return ResponseEntity.badRequest().body("未授权或登陆超时"+e.getMessage());
//    }

    @ExceptionHandler(LoginTooBusyException.class)
    public ResponseEntity unexpectedException(LoginTooBusyException e){
        return ResponseEntity.badRequest().body("密码出错次数过多，请在5分钟后重试");
    }

    @ExceptionHandler(RpcException.class)
    public ResponseEntity rpcException(RpcException e){
        return ResponseEntity.badRequest().body("接口调用异常");
    }

    @ExceptionHandler(RemotingException.class)
    public ResponseEntity remotingException(RemotingException e){
        return ResponseEntity.badRequest().body("远程调用失败");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity accessDeniedException(AccessDeniedException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }


}
