package com.front.api;

import com.api.user.UserService;
import com.common.pojo.ShiroUser;
import com.common.utils.EncryptUtils;
import com.common.utils.ResultBody;
import com.common.utils.Results;
import com.google.common.base.Charsets;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@RestController
@RequestMapping("/user")
public class UserController {


    @Reference
    private UserService userService;

    /***
     * 将userService纳入bean factory的管理中去，以便shiro realm中获取dubbo接口
     * @return
     */
    @Bean("userService")
    UserService userService(){
        return userService;
    }

    /****
     * 这边再登陆完成后需要再次请求一次才会把另一个请求顶掉（前端强制成功后跳转）
     * @param user
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "user", required = true, name = "用户实体类"),
            @ApiImplicitParam(value = "request", required = true, name = "request对象"),
            @ApiImplicitParam(value = "response", required = true, name = "response对象")
    })
    public ResultBody login(@Valid ShiroUser user, HttpServletRequest request, HttpServletResponse response, BindingResult result) throws AuthenticationException{
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            return Results.BAD__REQUEST.result("username or password should not be null", errors);
        }
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            ShiroUser shiroUser = (ShiroUser) subject.getPrincipal();
            if(shiroUser.getUsername().equals(user.getUsername())){
                return Results.SUCCESS.result("已登录", null);
            }else{
                subject.logout();//登出当前账号
            }
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), EncryptUtils.MD5Encode(user.getPassword(), Charsets.UTF_8.name()));
        if(user.getRememberMe()){
            token.setRememberMe(true);
        }
        subject.login(token);
        request.getSession().setAttribute("username", user.getUsername());
        String access_token = EncryptUtils.encode(user.getUsername(), user.getUsername());
        response.addCookie(new Cookie("access_token", access_token));
        response.addHeader("access_token", access_token);
        response.addHeader("refresh", "3;url='/user/checkPermission'");
        return Results.SUCCESS.result("login success, you'll be redirect to homepage in 3sec", null);
    }

    @GetMapping("/register")
    public ResultBody register(@Valid ShiroUser shiroUser, BindingResult result, HttpServletRequest request, HttpServletResponse response){
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            return Results.BAD__REQUEST.result("username or password should not be null", errors);
        }
        shiroUser.setPassword(EncryptUtils.MD5Encode(shiroUser.getPassword(), Charsets.UTF_8.name()));
        shiroUser = userService.register(shiroUser);
        if(shiroUser == null){
            return Results.BAD__REQUEST.result("register failed, nick name should be unique", null);
        }
        UsernamePasswordToken token = new UsernamePasswordToken(shiroUser.getUsername(), shiroUser.getPassword());
        SecurityUtils.getSubject().login(token);
        response.addCookie(new Cookie("username", shiroUser.getUsername()));
        response.addCookie(new Cookie("access_token", EncryptUtils.encode(shiroUser.getUsername(), shiroUser.getUsername())));
        response.addHeader("refresh", "3;url='/success'");
        return Results.SUCCESS.result("register success, you'll be redirect to homepage in 3sec", null);
    }

    @GetMapping("/isRegister")
    public ResultBody isRegister(@RequestParam(value = "username", required = true)String username){
        ShiroUser u = new ShiroUser();
        u.setUsername(username);
        u = userService.login(u);
        if(u.getId() == null){
            return Results.SUCCESS.result("", null);
        }
        return Results.SUCCESS.result("用户名已存在", null);
    }

    @GetMapping("/checkPermission")
    @RequiresPermissions("search")
    public ResultBody check(){
        return Results.SUCCESS.result("ok,you have permission", null);
    }


    @GetMapping("/getInfo")
    @RequiresPermissions("search")
    public ResultBody getInfo(){

        return Results.SUCCESS.result("success", null);
    }

    @GetMapping("/logout")
    public ResultBody logout(HttpServletRequest request){
        Subject subject = SecurityUtils.getSubject();
        if(subject.getPrincipal() != null){
            subject.logout();
        }
        return Results.SUCCESS.result("登出成功", null);
    }
}
