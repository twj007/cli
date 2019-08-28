package com.backend.dao.user;

import com.common.pojo.ShiroUser;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IUserDao {

    ShiroUser login(ShiroUser user);

    Set<String> getRoles(Long id);

    Set<String> getPermission(Long id);

    Long register(ShiroUser shiroUser);
}
