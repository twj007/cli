package com.backend.dao.admin;

import com.common.pojo.role.Role;

import java.util.List;

public interface IAdminDao {

    List<Role> getRoles(Role role);

    Long saveRole(Role role);

    Long updateRole(Role role);
}
