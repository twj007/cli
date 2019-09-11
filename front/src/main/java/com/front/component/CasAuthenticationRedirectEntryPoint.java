//package com.front.component;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///***
// **@project: cli
// **@description:
// **@Author: twj
// **@Date: 2019/09/11
// **/
//public class CasAuthenticationRedirectEntryPoint extends CasAuthenticationEntryPoint {
//
//    private String redirectUrl=null;
//
//    @Override
//    protected String createServiceUrl(final HttpServletRequest request, final HttpServletResponse response) {
//        if(redirectUrl==null)
//            redirectUrl = getServiceProperties().getService();
//        if(redirectUrl!=null){
//            String ctx=request.getContextPath();
//            String queryString=request.getQueryString();
//            String requestURI=request.getRequestURI();
//            requestURI=requestURI.substring(requestURI.indexOf(ctx)+ctx.length(),requestURI.length());
//            String serviceUrl="";
//            if(!requestURI.equals("/") && requestURI.length()>0){
//                serviceUrl="?"+ "";
//                serviceUrl+="="+requestURI;
//                if(StringUtils.isNotBlank(queryString)){
//                    serviceUrl+="?"+queryString;
//                }
//            }
//            getServiceProperties().setService(serviceUrlBak+serviceUrl);
//        }
//        return super.createServiceUrl(request, response);
//    }
//}
