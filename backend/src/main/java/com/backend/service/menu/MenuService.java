package com.backend.service.menu;

import com.backend.dao.menu.IMenuDao;
import com.common.pojo.ShiroUser;
import com.common.pojo.user.Menu;
import com.common.utils.TreeUtils;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/02
 **/
@Service(interfaceClass = com.api.menu.MenuService.class, filter = "RpcFilter", timeout = 10000)
public class MenuService implements com.api.menu.MenuService {

    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private IMenuDao menuDao;

    /***
     * 递归获取单级menu
     * @param m
     * @return
     */
    @Override
    public List<Menu> getMenu(Menu m) {
        List<Menu> Menu = menuDao.getMenu(m);
        return Menu;
    }

    @Override
    public List<Menu> getMenuListByUser(ShiroUser user) {
        List<Menu> menus =  menuDao.getMenuListByUser(user.getId());
        //从根节点递归菜单
        menus = TreeUtils.getChildPerms(menus, 0L);
        return menus;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Menu saveMenu(Menu menu) {
        Long num = menuDao.saveMenu(menu);
        if(num == 01L){
            Long n = menuDao.saveMenuRelation(menu);
            if(n != 01L){
                logger.error("method: {saveMenu}, error: {save menu relation failed}");
                throw new RuntimeException("method: {saveMenu}, error: {save menu relation failed}");
            }
            return menu;
        }else{
            logger.error("method: {saveMenu}, error: {save menu failed}");
            throw new RuntimeException("method: {saveMenu}, error: {save menu failed}");
        }
    }

    @Override
    public Long updateMenu(Menu menu) {
        return menuDao.updateMenu(menu);
    }

    @Override
    public Long deleteMenu(Menu menu) {
        return menuDao.updateMenu(menu);
    }


}
