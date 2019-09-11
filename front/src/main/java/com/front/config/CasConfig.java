package com.front.config;

import com.front.component.CacheUserDetailService;
import com.front.component.CasUserDetailService;
import com.front.component.SecurityRedisUserCacheManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.authentication.SpringCacheBasedTicketCache;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.config.authentication.CachingUserDetailsService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import javax.servlet.http.HttpSessionEvent;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/09
 **/
@Configuration
public class CasConfig {

    @Value("${cas.server.url}")
    private String casUrl;

    @Value("${server.port}")
    private String port;

    @Value("${server.address}")
    private String address;

    @Bean
    public ServiceProperties serviceProperties() {
        ServiceProperties serviceProperties = new ServiceProperties();
        //本机服务，访问/login/cas时进行校验登录
        serviceProperties.setService("http://"+ address + ":" + port + "/login/cas");
        serviceProperties.setSendRenew(false);
        return serviceProperties;
    }

    @Bean
    @Primary
    public AuthenticationEntryPoint authenticationEntryPoint(
            ServiceProperties sP) {

        CasAuthenticationEntryPoint entryPoint
                = new CasAuthenticationEntryPoint();
        //cas登录服务
        entryPoint.setLoginUrl(casUrl + "/login");
        entryPoint.setServiceProperties(sP);
        return entryPoint;
    }

    @Bean
    public TicketValidator ticketValidator() {
        //指定cas校验器
        return new Cas30ServiceTicketValidator(
                casUrl);
    }


    //cas认证
    @Bean
    public CasAuthenticationProvider casAuthenticationProvider(@Qualifier("securityRedisManager")SecurityRedisUserCacheManager manager) {

        CasAuthenticationProvider provider = new CasAuthenticationProvider();
        provider.setServiceProperties(serviceProperties());
        provider.setTicketValidator(ticketValidator());
        provider.setUserDetailsService(new CacheUserDetailService(manager, new CasUserDetailService()));
        //固定响应用户，在生产环境中需要额外设置用户映射
//        provider.setUserDetailsService(
//                s -> {
//                    System.out.println(s);
//                    return new User("auth-user", "123", true, true, true, true,
//                        AuthorityUtils.createAuthorityList("ROLE_ADMIN"));});
        provider.setKey("CAS_PROVIDER_LOCALHOST_8005");
        return provider;
    }


    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public LogoutFilter logoutFilter() {
        //退出后转发路径
        LogoutFilter logoutFilter = new LogoutFilter(
                casUrl + "/logout",
                securityContextLogoutHandler());
        //cas退出
        logoutFilter.setFilterProcessesUrl("/logout/cas");
        return logoutFilter;
    }

    @Bean
    public SingleSignOutFilter singleSignOutFilter() {
        //单点退出
        SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
        singleSignOutFilter.setCasServerUrlPrefix(casUrl);
        singleSignOutFilter.setIgnoreInitConfiguration(true);
        return singleSignOutFilter;
    }

    //设置退出监听
    @EventListener
    public SingleSignOutHttpSessionListener singleSignOutHttpSessionListener(
            HttpSessionEvent event) {
        return new SingleSignOutHttpSessionListener();
    }

}
