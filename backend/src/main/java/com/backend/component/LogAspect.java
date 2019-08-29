package com.backend.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/29
 **/
@Aspect
@Order(1)
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    private ThreadLocal<Long> count = new ThreadLocal<>();

    @Pointcut("execution(public * com.backend.service.*.*.*(..))")
    public void log(){}

    @Before("log()")
    public void before(JoinPoint point){
        long start = System.currentTimeMillis();
        count.set(start);
        Object[] args = point.getArgs();
        logger.info("【invoke service start】 args: {}", args);

    }

    @After("log()")
    public void after(JoinPoint point){
        long end = System.currentTimeMillis();
        long start = count.get();
        count.remove();
        logger.info("【invoke service end】 time used:{}ms", (end-start));
    }
}
