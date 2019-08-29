package com.front.component;

import com.common.pojo.RequestRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/29
 **/
@Component
public class LogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    ThreadLocal<Long> count = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        count.set(start);
        RequestRecord requestRecord = new RequestRecord(request.getMethod(), request.getRemoteAddr(), request.getRemotePort(), request.getRequestURI(), start, request.getQueryString());
        logger.info("【request start】: record: {}", requestRecord);
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long end = System.currentTimeMillis();
        long start = count.get();
        logger.info("【request end】: time used:{}ms", (end-start));
        count.remove();
        super.postHandle(request, response, handler, modelAndView);
    }
}
