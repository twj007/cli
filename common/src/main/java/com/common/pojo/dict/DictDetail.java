package com.common.pojo.dict;

import lombok.*;

import java.util.Date;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/30
 **/
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DictDetail {

    private Long id;
    private Long parentId;
    private String keyType;
    private String value;
    private Date createdate;
    private Date updatedate;
    private String status;
    private String description;
}
