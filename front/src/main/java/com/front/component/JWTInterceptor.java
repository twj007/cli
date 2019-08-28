package com.front.component;

import com.alibaba.nacos.client.utils.JSONUtils;
import com.common.utils.EncryptUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/***
 **@project: base
 **@description: jwt interceptor
 **@Author: twj
 **@Date: 2019/07/12
 **/
@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("access_token");
        if(EncryptUtils.verify(token)){
            return super.preHandle(request, response, handler);
        }else{
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            Map<String, String> res = new HashMap<>();
            res.put("msg", "验证失败，请重新登陆");
            res.put("code", "500");
            String result = JSONUtils.serializeObject(res);
            out.append(result);
            return false;
        }
    }

}
