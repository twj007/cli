package com.common.utils;

/***
 * 通过枚举的方式返回结果
 */
public enum Results {

    SUCCESS(200),

    BAD__REQUEST(500),

    NOt_FOUND(400),

    FORBIDDEN(0),

    TOKEN_EXPIRED(1403),

    SOLD_OUT(666),

    TIME_OUT(1000),

    REPEAT_REQUEST(1405);



    private final int code;

    Results(int code) {
        this.code = code;
    }

    public <T> ResultBody<T> result(String msg, T body){
        return ResultBody.result(code, msg, body);
    }
}
