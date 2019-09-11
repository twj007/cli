package com.front.component;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/11
 **/
public class CacheUserDetailService implements UserDetailsService {

    private CacheManager cacheManager;

    private CasUserDetailService casUserDetailService;

    public CacheUserDetailService(CacheManager cacheManager, CasUserDetailService casUserDetailService) {
        this.cacheManager = cacheManager;
        this.casUserDetailService = casUserDetailService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Cache userCache = cacheManager.getCache("Authentication");
        if(userCache == null){return null;}
        UserDetails user = userCache.get(s) == null ? null : (UserDetails) userCache.get(s).get();
        if(user == null){
            user = casUserDetailService.loadUserByUsername(s);
            userCache.put( s, user);
        }
        return user;
    }
}
