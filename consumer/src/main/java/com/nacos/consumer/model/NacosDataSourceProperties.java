package com.nacos.consumer.model;

import com.alibaba.cloud.sentinel.datasource.config.AbstractDataSourceProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/26
 **/
@Slf4j
@Getter
@Setter
public class NacosDataSourceProperties extends AbstractDataSourceProperties {

    private String serverAddr;
    private String groupId;
    private String dataId;

    // commercialized usage

    private String endpoint;
    private String namespace;
    private String accessKey;
    private String secretKey;

    public NacosDataSourceProperties(String factoryBeanName) {
        super(factoryBeanName);
    }
}