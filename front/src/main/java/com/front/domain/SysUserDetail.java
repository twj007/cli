package com.front.domain;

import com.common.pojo.SysUser;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/09
 **/
public class SysUserDetail implements UserDetails {

    private SysUser sysUser;

    public SysUserDetail(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public SysUserDetail() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        sysUser.getRoles().stream().forEach(role -> roles.add(new SimpleGrantedAuthority("ROLE_" + role)));
        sysUser.getPerms().stream().forEach(perms -> roles.add(new SimpleGrantedAuthority(perms)));
        return roles;
    }

    @Override
    public String getPassword() {
        return sysUser.getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "2".equals(sysUser.getStatus());
    }


}
