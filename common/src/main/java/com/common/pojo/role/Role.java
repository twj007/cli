package com.common.pojo.role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/02
 **/
@Getter
@Setter
@ToString
public class Role {

    private Long id;

    private String name;

    private Date createdate;

    private Date updaedate;

    private String status;

    private String desc;

    private Long userId;
}
