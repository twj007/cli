package com.api.admin;

import com.common.pojo.role.Role;

import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/02
 **/
public interface AdminService {

    Role saveRole(Role role);

    List<Role> getRoles(Role role);

    Long updateRole(Role role);

    Long updateRoleStatus(Role role);
}
