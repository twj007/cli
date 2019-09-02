package com.backend.component;

import com.common.constant.CommonConstants;
import com.common.utils.EncryptUtils;
import com.google.common.base.Charsets;
import org.apache.dubbo.rpc.*;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;

import static com.common.utils.EncryptUtils.AESDecode;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/08/28
 * AES(app name + 时间戳)
 **/
@Component
public class RpcFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String appCode = RpcContext.getContext().getAttachment("appCode");
        String AESCode = RpcContext.getContext().getAttachment("aesCode");
        String dateTime = RpcContext.getContext().getAttachment("timestamp");
        if(StringUtils.isEmpty(appCode) || StringUtils.isEmpty(AESCode)){
            throw new RpcException("invalid attachment!");
        }
        String code = EncryptUtils.AESDecode(AESCode);
        if(!appCode.equals(code + dateTime)){
            throw new RpcException("invalid app code");
        }
        return invoker.invoke(invocation);
    }
}
