//package com.front.component;
//
//import com.api.user.UserService;
//import com.common.pojo.SysUser;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.cas.CasRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Value;
//
///***
// **@project: cli
// **@description:
// **@Author: twj
// **@Date: 2019/09/10
// **/
//public class CasAuthenticationRealm extends CasRealm {
//
//    private UserService userService;
//
////    @Value("${cas.server.url}")
//    private String casUrl = "http://127.0.0.1:8443/cas";
//
////    @Value("${server.address}")
//    private String address = "127.0.0.1";
//
////    @Value("${server.port}")
//    private String port = "8005";
//
//    // 获取授权信息
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(
//            PrincipalCollection principals) {
//        String principal = (String) principals.getPrimaryPrincipal();
//        userService = SpringBeanFactoryUtils.getBean("userService", UserService.class);
//        SysUser user = userService.getByUsername(principal);
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        authorizationInfo.setRoles(user.getRoles());
//        authorizationInfo.setStringPermissions(user.getPerms());
//        return authorizationInfo;
//    }
//
//    public String getCasServerUrlPrefix() {
//        return casUrl + "/login";
//    }
//
//    public String getCasService() {
//        return "http://" + address + ":" + port;
//    }
//
//}
