package com.common.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/27
 **/
@Getter
@Setter
public class RequestRecord {

    private String uri;

    private Date createDate;

    private Date expireDate;

    private Integer port;

    private String address;

    private Integer count;
}
