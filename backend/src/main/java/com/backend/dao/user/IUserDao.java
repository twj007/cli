package com.backend.dao.user;

import com.common.pojo.ShiroUser;
import com.common.pojo.user.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IUserDao {

    ShiroUser login(ShiroUser user);

    Set<String> getRoles(Long id);

    Set<String> getPermission(Long id);

    Long register(ShiroUser shiroUser);

    List<Menu> getMenu(Menu menu);

    Long getRecord(Long id);
}
