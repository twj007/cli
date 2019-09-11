package com.front.component;

import com.api.user.UserService;
import com.common.pojo.SysUser;
import com.front.domain.SysUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.cache.NullUserCache;


/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/09
 **/
public class CasUserDetailService implements UserDetailsService {


    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String principal) throws UsernameNotFoundException {
        if(userService == null) {
            synchronized (this) {
                userService = SpringBeanFactoryUtils.getBean("userService", UserService.class);
            }
        }

        SysUser user = userService.getById(Long.valueOf(principal));
        if(user != null){
            SysUserDetail userDetail = new SysUserDetail(user);

            return userDetail;
        }else{
            throw new UsernameNotFoundException("用户名或密码错误");
        }

    }

}
