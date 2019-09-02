package com.backend.dao.menu;

import com.common.pojo.user.Menu;

import java.util.List;

public interface IMenuDao {

    List<Menu> getMenu(Menu m);

    List<Menu> getMenuListByUser(Long id);

    Long saveMenu(Menu menu);

    Long saveMenuRelation(Menu menu);

    Long updateMenu(Menu menu);

    Long updateMenuRelation(Menu menu);
}
