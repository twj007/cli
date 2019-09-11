package com.front.api.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/11
 **/
@Controller("/login")
public class LoginController {

    @Value("${cas.server.url}")
    private String casUrl;


    @GetMapping("/cas/login")
    public void toCasLogin(HttpServletResponse response) throws IOException {
        response.sendRedirect(casUrl + "/login");
    }

    @GetMapping("/cas/logout")
    public void toCasLogout(HttpServletResponse response) throws IOException {
        response.sendRedirect(casUrl + "/logout");
    }


}
