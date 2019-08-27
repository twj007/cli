package com.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/12
 **/
public class ResultBody<T> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(ResultBody.class);

    private int code;

    private String msg;

    private T body;

    private ResultBody() {
    }

    private ResultBody(int code, String msg, T body){
        logger.info("【返回数据】code: {}, msg: {}, body:{}", code, msg, body);
        this.code = code;
        this.msg = msg;
        this.body = body;
    }

    public static <T> ResultBody<T> result(int code, String msg, T body){
        return new ResultBody<T>(code, msg, body);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
