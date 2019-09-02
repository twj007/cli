package com.backend.service.admin;

import com.backend.dao.admin.IAdminDao;
import com.common.pojo.role.Role;
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
@Service(interfaceClass = com.api.admin.AdminService.class, filter = "RpcFilter", timeout = 10000)
public class AdminService implements com.api.admin.AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private IAdminDao adminDao;

    @Override
    @Transactional(propagation =  Propagation.REQUIRED, rollbackFor = Exception.class)
    public Role saveRole(Role role) {
        Long num = adminDao.saveRole(role);
        if(num == 01L){
            return role;
        }else{
            logger.error("method: {saveRole}, error: {save role failed}");
            throw new RuntimeException("method: {saveMenu}, error: {save menu failed}");
        }
    }

    @Override
    public List<Role> getRoles(Role role) {
        return adminDao.getRoles(role);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Long updateRole(Role role) {
        return adminDao.updateRole(role);
    }

    @Override
    public Long updateRoleStatus(Role role) {
        return adminDao.updateRole(role);
    }
}
