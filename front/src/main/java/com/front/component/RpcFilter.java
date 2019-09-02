package com.front.component;

import com.common.utils.EncryptUtils;
import org.apache.dubbo.rpc.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Date;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 **/
@Component
@RefreshScope
public class RpcFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String appCode = invoker.getUrl().getParameter("application");
        Long dateTime = new Date().getTime();
        String encryptedCode = EncryptUtils.AESEncode(appCode + String.valueOf(dateTime));
        RpcContext.getContext().setAttachment("appCode", appCode);
        RpcContext.getContext().setAttachment("aesCode", encryptedCode);
        RpcContext.getContext().setAttachment("timestamp", String.valueOf(dateTime));
        return invoker.invoke(invocation);
    }
}
