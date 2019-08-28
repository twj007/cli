package com.common.pojo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/15
 **/
public class ShiroUser implements Serializable {

    private Long id;       // 用户id
    @NotNull(message = "用户名不为空")
    private String username;   // 登录名，不可改
    private String nickname;    // 用户昵称，可改
    @NotNull(message = "密码不为空")
    @Length(min = 6, message = "密码不少于6位")
    private String password;     // 已加密的登录密码
    private String salt;    // 加密盐值
    private Date createdate;   // 创建时间
    private Date updatedate;   // 修改时间
    private Set<String> roles = new HashSet<>();    //用户所有角色值，用于shiro做角色权限的判断
    private Set<String> perms = new HashSet<>();
    private boolean rememberMe;

    public ShiroUser(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public ShiroUser() {
    }

    public boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nick) {
        this.nickname = nick;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

    @Override
    public String toString() {
        return "ShiroUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nick='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", createdate=" + createdate +
                ", updatedate=" + updatedate +
                ", roles=" + roles +
                ", perms=" + perms +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
