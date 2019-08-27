package com.backend.component;

/***
 **@project: base
 **@description: it's context of  datasource
 **@Author: twj
 **@Date: 2019/06/19
 **/
public class DatasourceContext {

    private static final ThreadLocal<String> targetDatasource = new ThreadLocal<>();

    public static final String DEFAULT_DATASOURCE = "master";

    public static void setDatesource(String name){
        targetDatasource.set(name);
    }

    public static String getDatesource(){
        return targetDatasource.get();
    }

    public static void removeDatasource(){
        targetDatasource.remove();
    }
}
