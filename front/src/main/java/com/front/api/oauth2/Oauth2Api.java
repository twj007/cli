package com.front.api.oauth2;

import io.swagger.annotations.Api;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/03
 **/
@RestController
@RefreshScope
@Api
@RequestMapping("/oauth2")
public class Oauth2Api {

    @Value("${oauth2.client.access_token_url}")
    private String accessTokenURL;

    @Value("${oauth2.client.user_info_url}")
    private String userInfoUrl;

    @Value("${oauth2.client.redirect_url}")
    private String redirectUrl;

    @Value("${oauth2.client.id}")
    private String clientId;

    @Value("${oauth2.client.secret}")
    private String secret;

    /***
     * 每次请求的请求头都会加上token， 通过拦截器对token是否过期等需要校验，如果过期，要做续签操作(oauth服务端做)
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/accessToken")
    public ResponseEntity accessToken(HttpServletRequest request) throws Exception{
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthClientRequest accessTokenRequest = OAuthClientRequest
                .tokenLocation(accessTokenURL) //获取token的url
                .setGrantType(GrantType.AUTHORIZATION_CODE) //授权类型是授权码
                .setClientId(clientId).setClientSecret(secret) // client id 以及密钥
                .setCode(request.getParameter("code"))
                .setRedirectURI(redirectUrl) // 授权码以及重定向的url
                .buildQueryMessage();
        //获取access token
        accessTokenRequest.addHeader("Accept", "application/json");
        accessTokenRequest.addHeader("Content-Type", "application/json");

        OAuthAccessTokenResponse oAuthResponse =
                oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);
        String accessToken = oAuthResponse.getAccessToken();
        Long expiresIn = oAuthResponse.getExpiresIn();
        //获取user info
        OAuthClientRequest userInfoRequest =
                new OAuthBearerClientRequest(userInfoUrl)
                        .setAccessToken(accessToken).buildQueryMessage();
        OAuthResourceResponse resourceResponse = oAuthClient.resource(
                userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        String username = resourceResponse.getBody();
        return ResponseEntity.ok(accessToken+":"+username);
    }


    @RequestMapping("/getUserInfo")
    public ResponseEntity getUserInfo(HttpServletRequest request) throws Exception{
        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        String accessToken = request.getHeader("token");
        OAuthClientRequest userInfoRequest =
                new OAuthBearerClientRequest(userInfoUrl)
                        .setAccessToken(accessToken).buildQueryMessage();
        OAuthResourceResponse resourceResponse = oAuthClient.resource(
                userInfoRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
        return ResponseEntity.ok(resourceResponse.getBody());
    }

}
