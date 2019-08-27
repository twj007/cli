package com.front.component;

import com.common.exception.LoginTooBusyException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/***
 * 手动注入。 shiroWebManager
 * 限制密码尝试次数
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 **/
public class PasswordRetryMatcher extends HashedCredentialsMatcher {

    @Autowired
    private RedisTemplate redisTemplate;


    /***
     * 这里对用户的登陆尝试进行限制，在5次内没能正确填写用户名密码时，将会抛出异常，
     * 而记录会在五分钟内从redis过期，之后才可重新访问
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        Integer count;
        if(!redisTemplate.hasKey(token.getPrincipal())){
            count = new Integer(1);
            redisTemplate.opsForValue().set(token.getPrincipal(), count, 300, TimeUnit.SECONDS);
        }else{
            count = (Integer) redisTemplate.opsForValue().get(token.getPrincipal());
            redisTemplate.opsForValue().set(token.getPrincipal(), count+1, 300, TimeUnit.SECONDS);
        }
        if(count != null && count.intValue() > 5){
            throw new LoginTooBusyException("【login failed】failed more than 5 times, this account will be denied in 5 minutes", (String)token.getPrincipal());
        }
        boolean match = super.doCredentialsMatch(token, info);
        if(match){
            redisTemplate.delete(token.getPrincipal());
            return true;
        }else{
            return false;
        }
    }
}
