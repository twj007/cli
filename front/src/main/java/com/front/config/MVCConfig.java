package com.front.config;

//import com.front.component.JWTInterceptor;
import com.front.component.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Configuration
public class MVCConfig extends WebMvcConfigurationSupport {

//    @Autowired
//    private JWTInterceptor jwtInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/user/**")
//                .excludePathPatterns("/user/login")
//                .excludePathPatterns("/user/register")
//                .excludePathPatterns("/user/isRegister")
//                .excludePathPatterns("/user/logout")
//                .order(2);
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/**")
                .order(1);
        super.addInterceptors(registry);
    }
}
