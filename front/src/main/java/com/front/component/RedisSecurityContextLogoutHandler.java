package com.front.component;

import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/11
 **/
public class RedisSecurityContextLogoutHandler extends SecurityContextLogoutHandler {

    private RedisTemplate redisTemplate;

    public RedisSecurityContextLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String principal = request.getUserPrincipal().getName();
        redisTemplate.delete("Authentication::" + principal);
        super.logout(request, response, authentication);
    }
}
