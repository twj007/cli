package com.backend.component;

import com.common.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/***
 **@project: base
 **@description: the main aspcet to switch datasource
 **@Author: twj
 **@Date: 2019/06/19
 **/
@Aspect
@Order(1)
@Component
public class DatasourceAspect {

    private static final Logger logger = LoggerFactory.getLogger(DatasourceAspect.class);

    @Before(value = "@annotation(com.common.annotation.DataSource)")
    public void siwtchDatasource(JoinPoint point){
        //获得当前访问的class
        Class<?> className = point.getTarget().getClass();
        //获得访问的方法名
        String methodName = point.getSignature().getName();
        //得到方法的参数的类型
        Class[] argClass = ((MethodSignature)point.getSignature()).getParameterTypes();
        String dataSource = DatasourceContext.DEFAULT_DATASOURCE;
        try {
            // 如果方法上有注解按照方法上的配置去设置数据源。
            // 不然再去检查类上是否有注解，有的话就设置数据源，不然的话使用的就是默认的数据源。
            // 优先级: 方法注解 > 类注解 > 默认数据源
            if(className.getMethod(methodName, argClass).isAnnotationPresent(DataSource.class)){
                // 得到访问的方法对象
                Method method = className.getMethod(methodName, argClass);
                DataSource annotation = method.getAnnotation(DataSource.class);
                dataSource = annotation.type();
            } else if(className.isAnnotationPresent(DataSource.class)){
                DataSource annotation  = className.getAnnotation(DataSource.class);
                dataSource = annotation.type();
            }
            logger.info("【datasource aspect】：当前数据源: {}", dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 切换数据源
        DatasourceContext.setDatesource(dataSource);
    }

    @After(value = "@annotation(com.common.annotation.DataSource)")
    public void clearDatasource(){
        DatasourceContext.removeDatasource();
    }

}
