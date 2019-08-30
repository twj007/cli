package com.common.pojo.dict;

import lombok.*;

import java.util.Date;
import java.util.List;

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
public class Dict {

    private Long id;
    private String keyType;
    private String value;
    private String description;
    private List<DictDetail> detail;
    private Date createdate;
    private Date updatedate;
    private String status;

}
