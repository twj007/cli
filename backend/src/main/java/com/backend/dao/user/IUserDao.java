package com.backend.dao.user;

import com.common.pojo.SysUser;
import com.common.pojo.user.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IUserDao {

    SysUser login(SysUser user);

    Set<String> getRoles(Long id);

    Set<String> getPermission(Long id);

    Long register(SysUser shiroUser);

    List<Menu> getMenu(Menu menu);

    Long getRecord(Long id);

    List<Menu> getMenuListByUser(@Param("id")Long id);

    SysUser getByUsername(@Param("username") String username);

    SysUser getById(@Param("id")Long username);
}