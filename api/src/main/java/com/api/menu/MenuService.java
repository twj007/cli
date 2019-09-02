package com.api.menu;

import com.common.pojo.ShiroUser;
import com.common.pojo.user.Menu;

import java.util.List;

public interface MenuService {

    //单层搜索
    List<Menu> getMenu(Menu m);

    List<Menu> getMenuListByUser(ShiroUser user);

    Menu saveMenu(Menu menu);

    Long updateMenu(Menu menu);

    Long deleteMenu(Menu menu);
}
