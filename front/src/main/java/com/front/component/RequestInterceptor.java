//package com.front.component;
//
//import com.alibaba.druid.support.json.JSONUtils;
//import com.mall.util.RepeatScan;
//import org.redisson.api.RMap;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///***
// **@project: base
// **@description:
// **@Author: twj
// **@Date: 2019/07/12
// **/
//@Component
//public class RequestInterceptor extends HandlerInterceptorAdapter {
//
//    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//        Method method = ((HandlerMethod)handler).getMethod();
//        Annotation annotation = method.getAnnotation(RepeatScan.class);
//        if(annotation == null){
//            return super.preHandle(request, response, handler);
//        }else if(((RepeatScan) annotation).scan()){
//            String token = request.getHeader("token");
//            if(token != null){
//                String URI;
//                switch(request.getMethod()){
//                    case "POST":
//                        Map<String, String[]> params = request.getParameterMap();
//                        StringBuffer stringBuffer = new StringBuffer();
//                        Iterator<Map.Entry<String, String[]>> iter = params.entrySet().iterator();
//                        while (iter.hasNext()){
//                            Map.Entry<String, String[]> kv = iter.next();
//                            stringBuffer.append(kv.getKey()).append("=");
//                            for(String val : kv.getValue()){
//                                stringBuffer.append(val).append(",");
//                            }
//                            stringBuffer.deleteCharAt(stringBuffer.length()-1);
//                            stringBuffer.append("&");
//                        }
//                        URI = stringBuffer.toString().substring(0, stringBuffer.toString().length()-1);
//                        break;
//                    case "GET":
//                        URI = request.getQueryString();
//                        break;
//                    default:
//                        URI = "";
//                        break;
//                }
//                if(!"".equals(URI)) {
//                    RMap<String, Integer> reqParams = redissonClient.getMap(token);
//                    if (reqParams.get(URI) == null) {
//                        reqParams.entrySet(URI, 1);
//
//                    } else {
//                        response.setContentType("application/json; charset=utf-8");
//                        response.setCharacterEncoding("UTF-8");
//                        PrintWriter out = response.getWriter();
//                        Map<String, String> res = new HashMap<>();
//                        res.put("msg", "请勿重复提交");
//                        res.put("code", "400");
//                        String result = JSONUtils.toJSONString(res);
//                        out.append(result);
//                        return false;
//                    }
//                }
//            }
//        }
//        return super.preHandle(request, response, handler);
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }
//}
