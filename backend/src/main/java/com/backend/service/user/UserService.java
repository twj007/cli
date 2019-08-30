package com.backend.service.user;

import com.backend.dao.user.IUserDao;
import com.common.annotation.DataSource;
import com.common.pojo.ShiroUser;
import com.common.pojo.user.Menu;
import com.common.pojo.user.UserInfo;
import com.common.utils.TreeUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Service(interfaceClass = com.api.user.UserService.class, filter = "RpcFilter", timeout = 10000)
public class UserService implements com.api.user.UserService {

    @Autowired
    private IUserDao userDao;

    @Override
    @DataSource
    public ShiroUser login(ShiroUser user) {
        return userDao.login(user);
    }

    @Override
    @DataSource
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ShiroUser register(ShiroUser shiroUser) {
        userDao.register(shiroUser);
        return shiroUser;
    }

    /***
     * 递归获取单级menu
     * @param m
     * @return
     */
    @Override
    public List<Menu> getMenu(Menu m) {
        List<Menu> Menu = userDao.getMenu(m);
        return Menu;
    }

    @Override
    public UserInfo getUserInfo(ShiroUser user) {
        //UserInfo info = userDao.getUserInfo(user);
        UserInfo info = null;
        return info;
    }

    @Override
    public List<Menu> getMenuListByUser(ShiroUser user) {
        List<Menu> menus =  userDao.getMenuListByUser(user.getId());
        //从根节点递归菜单
        menus = TreeUtils.getChildPerms(menus, 0L);
        return menus;
    }



    @DataSource(type = "slaver")
    @Transactional(readOnly = true)
    public Long getRecord(Long id){
        return userDao.getRecord(id);
    }
}
