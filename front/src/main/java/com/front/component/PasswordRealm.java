package com.front.component;

import com.common.pojo.ShiroUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.HashSet;
import java.util.Set;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 **/
public class PasswordRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(principalCollection == null){
            throw new AuthorizationException("user principal is null");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(principalCollection.asSet());
        info.setRoles(((ShiroUser)principalCollection.getPrimaryPrincipal()).getRoles());
        return info;
    }

//    public static void main(String[] args) {
//        DefaultPasswordService passwordService = new DefaultPasswordService();
//        String encrypted = passwordService.encryptPassword("123456");
//        System.out.println(passwordService.passwordsMatch("123456", encrypted));
//    }

    /***
     * 这里是通过自定义的matcher去校验数据， 通过在数据库中查出的数据去创建校验info去和token中的值做比较，比较不同则抛出自定义异常，并对异常数据处理
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

//        String password = passwordService.encryptPassword(new String((char[])authenticationToken.getCredentials()));
//        System.out.println(password);
        ShiroUser user = new ShiroUser();
        Set<String> premissions = new HashSet<>();
        premissions.add("customer:search");
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        user.setUsername("jien007");
        user.setPassword("123456");
        user.setPerms(premissions);
        user.setRoles(roles);
        //如果需要的话，可以设置盐值
        String password = new SimpleHash("md5", user.getPassword(), null, 2).toString();
        return new SimpleAuthenticationInfo(user, password, user.getUsername());
    }
}
