package com.backend.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.util.StringUtils;

/***
 **@project: base
 **@description: switch datasource
 **@Author: twj
 **@Date: 2019/06/19
 **/
public class DynamicDatasource extends AbstractRoutingDataSource {

    private static final Logger logger = LoggerFactory.getLogger(DynamicDatasource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DatasourceContext.getDatesource();
        if(StringUtils.isEmpty(datasource)){
            logger.error("【datasource】数据源为空, 使用默认数据源");
            datasource = DatasourceContext.DEFAULT_DATASOURCE;
        }else{
            logger.info("【datasource】数据源为: {}", datasource);
        }
        return datasource;
    }
}
