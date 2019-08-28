package com.front.config;

import com.api.user.UserService;
import com.front.component.KickOutSessionFilter;
import com.front.component.PasswordRealm;
import com.front.component.PasswordRetryMatcher;
import com.front.component.ShiroRedisManager;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import javax.servlet.Filter;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/15
 **/
@Configuration
public class ShiroConfig {

    @Bean("myRealm")
    PasswordRealm myRealm(@Qualifier("shiroRedisManager")ShiroRedisManager shiroRedisManager,
                          @Qualifier("hashedCredentialsMatcher")HashedCredentialsMatcher matcher){

        PasswordRealm realm = new PasswordRealm();
        realm.setCachingEnabled(true);
        realm.setCacheManager(shiroRedisManager);
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthenticationCacheName("Authentication");
        realm.setAuthorizationCacheName("Authorization");
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        /**
         * setUsePrefix(false)用于解决一个奇怪的bug。在引入spring aop的情况下。
         * 在@Controller注解的类的方法中加入@RequiresRole注解，会导致该方法无法映射请求，导致返回404。
         * 加入这项配置能解决这个bug
         */
        creator.setUsePrefix(true);
        return creator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

//    @Bean
//    public SecurityManager securityManager(){
//        SecurityManager manager = new DefaultSecurityManager();
//        ((DefaultSecurityManager) manager).setRealm(myRealm());
//        return manager;
//    }

    /***
     *
     * @return
     */
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chain =  new DefaultShiroFilterChainDefinition();
        chain.addPathDefinition("/", "anon");
        chain.addPathDefinition("/static/**", "anon");
        chain.addPathDefinition("/user/login", "anon");
        chain.addPathDefinition("/register", "anon");
        chain.addPathDefinition("/admin", "authc,roles[admin]");
        chain.addPathDefinition("/**", "authc");
        return chain;
    }

    /***
     * 需要自定义连接器时通过这个去配置
     * @return
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("webSecurityManager") DefaultWebSecurityManager webSecurityManager,
                                                  @Qualifier("shiroRedisManager") ShiroRedisManager sessionManager,
                                                  @Qualifier("redisTemplate") RedisTemplate redisTemplate){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        //拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //配置filterMap的时候，范围大的放在后面，不然会被覆盖权限
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/v1", "anon");
        filterChainDefinitionMap.put("/isRegister", "anon");
        filterChainDefinitionMap.put("/accessToken", "anon");
        filterChainDefinitionMap.put("/auth", "anon");
        filterChainDefinitionMap.put("/getUserInfo", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/user/login", "anon,kickout");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/admin", "authc,roles[admin]");
        filterChainDefinitionMap.put("/**", "authc,kickout");

        // 添加自己的过滤器并且取名
        Map<String, Filter> filterMap = new HashMap<>(16);
//        filterMap.put("forceLogout", new ForceLogoutFilter());
        filterMap.put("kickout", new KickOutSessionFilter(1, true, sessionManager, redisTemplate));
        shiroFilterFactoryBean.setFilters(filterMap);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        shiroFilterFactoryBean.setLoginUrl("/user/logout");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unAuthorized");
        return shiroFilterFactoryBean;
    }



    SimpleCookie simpleCookie(){
        SimpleCookie rememberMe = new SimpleCookie("remember");
        rememberMe.setMaxAge(60 * 60 * 60);
        return rememberMe;
    }

    /***
     * 在session过期后通过remember校验并且通过缓存管理器将数据存储到redis中
     * @return
     */
    @Bean
    CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(simpleCookie());
        manager.setCipherKey(Base64.getDecoder().decode("2AvVhdsgUs0FSA3SDFAdag=="));
        return manager;
    }


    /**
     * maxAge -1 代表 关闭浏览器 session即失效
     * @return
     */
    SimpleCookie sessionIdCookie(){
        SimpleCookie sessionIdCookie = new SimpleCookie("SESSION");
        sessionIdCookie.setMaxAge(3600);
        return sessionIdCookie;
    }

    @Bean("sessionManager")
    SessionManager sessionManager(@Qualifier("shiroRedisManager")ShiroRedisManager shiroRedisManager){
        DefaultWebSessionManager manager = new DefaultWebSessionManager();
        manager.setSessionIdCookie(sessionIdCookie());
        manager.setCacheManager(shiroRedisManager);
        return manager;
    }

    @Bean("webSecurityManager")
    DefaultWebSecurityManager webSecurityManager(@Qualifier("myRealm") PasswordRealm myRealm,
                                                 @Qualifier("shiroRedisManager")ShiroRedisManager shiroRedisManager){
                                                 //@Qualifier("sessionManager")SessionManager sessionManager){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRememberMeManager(rememberMeManager());
        manager.setCacheManager(shiroRedisManager);
        //manager.setSessionManager(sessionManager);
        manager.setRealm(myRealm);
        return manager;
    }

    @Bean
    SessionDAO sessionDao(@Qualifier("shiroRedisManager") ShiroRedisManager cacheManager){
        EnterpriseCacheSessionDAO sessionDAO = new EnterpriseCacheSessionDAO();
        sessionDAO.setCacheManager(cacheManager);
        return sessionDAO;
    }

    @Bean("passwordService")
    PasswordService passwordService(){
        return new DefaultPasswordService();
    }

    @Bean
    HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher matcher = new PasswordRetryMatcher();
        matcher.setHashAlgorithmName("md5");//算法
        matcher.setHashIterations(2);//加密次数
        matcher.setStoredCredentialsHexEncoded(true);//16进制格式存储
        return matcher;
    }

}
