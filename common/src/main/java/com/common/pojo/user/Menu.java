package com.common.pojo.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/29
 **/
@Getter
@Setter
@ToString
public class Menu {

    private Long id;
    private String name;
    private String desc;
    private String url;
    private Date createdate;
    private String icon;
    private List<Menu> child;
    private Long pId;
    private Long userId;
    private Long roleId;
}
