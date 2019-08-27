package com.common.annotation;

import java.lang.annotation.*;

/***
 * 通过 master 和 slaver 来切换数据源
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String type() default "master";
}
