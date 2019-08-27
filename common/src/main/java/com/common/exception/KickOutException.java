package com.common.exception;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 **/
public class KickOutException extends RuntimeException {

    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public KickOutException(String message) {
        super(message);
    }

    public KickOutException(String message, String sessionId) {
        super(message);
        this.sessionId = sessionId;
    }

    public KickOutException(String message, Throwable cause, String sessionId) {
        super(message, cause);
        this.sessionId = sessionId;
    }
}
