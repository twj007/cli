package com.backend.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.backend.component.DynamicDatasource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/***
 **@project: base
 **@description: datasource and other
 **@Author: twj
 **@Date: 2019/06/19
 * aop 内的配置
 **/
@Configuration
public class JdbcConfig {

    private static final Logger logger = LoggerFactory.getLogger(JdbcConfig.class);

    @Value("${spring.datasource.druid.slaver.url}")
    private String sUrl;

    @Value("${spring.datasource.druid.slaver.username}")
    private String sUsername;

    @Value("${spring.datasource.druid.slaver.password}")
    private String sPassword;

    @Value("${spring.datasource.druid.slaver.driver-class-name}")
    private String sDriver;

    @Value("${spring.datasource.druid.master.url}")
    private String mUrl;

    @Value("${spring.datasource.druid.master.username}")
    private String mUsername;

    @Value("${spring.datasource.druid.master.password}")
    private String mPassword;

    @Value("${spring.datasource.druid.slaver.driver-class-name}")
    private String mDriver;

    @Bean("masterDatasource")
    public DataSource masterDataSource(){
        logger.debug("【jdbcConfig】: 初始化master数据源");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(mDriver);
        dataSource.setUrl(mUrl);
        dataSource.setUsername(mUsername);
        dataSource.setPassword(mPassword);
        return dataSource;
    }

    @Bean("slaverDatasource")
    public DataSource slaverDataSource(){
        logger.debug("【jdbcConfig】: 初始化slaver数据源");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(sDriver);
        dataSource.setUrl(sUrl);
        dataSource.setUsername(sUsername);
        dataSource.setPassword(sPassword);
        return dataSource;
    }

    @Bean("dynamicDatasource")
    public DataSource dynamicDatasource(){
        logger.debug("【jdbcConfig】: 初始化动态数据源");
        DynamicDatasource dynamicDatasource = new DynamicDatasource();
        DataSource masterDatasource = masterDataSource();
        DataSource slaverDatasource = slaverDataSource();
        Map<Object, Object> datasources = new HashMap<>();
        datasources.put("master", masterDatasource);
        datasources.put("slaver", slaverDatasource);
        dynamicDatasource.setTargetDataSources(datasources);
        return dynamicDatasource;
    }

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect", "Mysql");
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDatasource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        bean.setTypeAliasesPackage("com.backend.pojo");
        bean.setPlugins(new Interceptor[]{new PageInterceptor()});
        bean.setDataSource(dataSource);
        return bean.getObject();
    }


    @Bean("transactionManager")
    public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dynamicDatasource")DataSource dataSource){
        DataSourceTransactionManager manager = new DataSourceTransactionManager();
        manager.setDataSource(dataSource);
        return  manager;
    }

}
