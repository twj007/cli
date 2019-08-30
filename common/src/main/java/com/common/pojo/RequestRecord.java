package com.common.pojo;

import lombok.*;

import java.util.Date;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/27
 **/
@Getter
@Setter
@ToString
public class RequestRecord {

    private String method;

    private String remoteAddr;

    private int remotePort;

    private String requestURI ;

    private String request;

    private long currentDate;

    private String args;

    private String clazz;

    public RequestRecord() {

    }
    public RequestRecord(String method, String remoteAddr, int remotePort, String requestURI, long date, String args) {
        this.method = method;
        this.remoteAddr = remoteAddr;
        this.remotePort = remotePort;
        this.requestURI = requestURI;
        this.currentDate = date;
        this.args = args;
    }
    public RequestRecord(String method, String remoteAddr, int remotePort, String requestURI, long date, String args, String clazz) {
        this.method = method;
        this.remoteAddr = remoteAddr;
        this.remotePort = remotePort;
        this.requestURI = requestURI;
        this.currentDate = date;
        this.args = args;
        this.clazz = clazz;
    }
}
