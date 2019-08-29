package com.front.component;


import com.api.user.UserService;
import com.common.pojo.ShiroUser;
import com.common.utils.EncryptUtils;
import com.google.common.base.Charsets;
import org.apache.dubbo.config.annotation.Reference;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 **/
public class PasswordRealm extends AuthorizingRealm {

    //这样注入为空，在配置文件中注册服务
//    @Reference(interfaceClass = UserService.class, lazy = true, check = false)
    private UserService userService;



    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(principalCollection == null){
            throw new AuthorizationException("user principal is null");
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(principalCollection.asSet());
        //设置角色， 权限
        info.setRoles(((ShiroUser)principalCollection.getPrimaryPrincipal()).getRoles());
        info.setStringPermissions(((ShiroUser) principalCollection.getPrimaryPrincipal()).getPerms());
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
        if(userService == null){
            userService = SpringBeanFactoryUtils.getBean("userService", UserService.class);
        }
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[])authenticationToken.getCredentials());
        ShiroUser user = new ShiroUser(username, password);
        user = userService.login(user);
        if(user == null){
            return null;
        }
        //如果需要的话，可以设置盐值
        String pass = new SimpleHash("md5", user.getPassword(), null, 2).toString();
        return new SimpleAuthenticationInfo(user, pass, user.getUsername());
    }
}
