package com.front.component;

import com.alibaba.nacos.client.utils.JSONUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/17
 * 由于集成了spring-session，通过shiroredismanager直接去获取spring-session的session，不然会获取不到session
 * 必须在登陆后请求一次才会把上一个账号给顶出去
 * 局限性很大，如果多个应用服务的话，根本没办法扩展 -> 缓存的数据存到redis，不通过内存缓存 或者使用别的方案
 **/
public class KickOutSessionFilter extends AccessControlFilter {
    private int maxSession;
    private boolean kickBefore;
    private ShiroRedisManager sessionManager;
//    private Map<String, Deque<Serializable>> cache;//使用内存缓存
    private RedisTemplate redisTemplate;

    public void setSessionManager(ShiroRedisManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public KickOutSessionFilter() {
    }

    public KickOutSessionFilter(int maxSession, boolean kickBefore, ShiroRedisManager sessionManager, RedisTemplate redisTemplate) {
        this.maxSession = maxSession;
        this.kickBefore = kickBefore;
        this.sessionManager = sessionManager;
//        this.cache = new HashMap<>();
        this.redisTemplate = redisTemplate;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }


    public void setKickBefore(boolean kickBefore) {
        this.kickBefore = kickBefore;
    }


    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        Session session = subject.getSession();
        String username = request.getParameter("username");
        String sessionId = "spring:session:sessions:"+session.getId();

//        //TODO 同步控制
//        Deque<Serializable> deque = cache.get(username);
//        if(deque == null) {
//            deque = new LinkedList<Serializable>();
//            cache.put(username, deque);
//        }

        List<String> online = redisTemplate.opsForList().range("online:"+username, 0, redisTemplate.opsForList().size("online:"+username));

        //如果队列里没有此sessionId，且用户没有被踢出；放入队列,且用户通过验证
        if((online == null || !online.contains(sessionId)) && ((ShiroRedisManager.ShiroRedisCache) sessionManager.getCache(sessionId.toString())).get("kickout") == null && subject.isAuthenticated()) {
            redisTemplate.opsForList().rightPush("online:"+username, sessionId);
        }
        String kickoutSessionId = null;
        //如果队列里的sessionId数超出最大会话数，开始踢人
        if (redisTemplate.opsForList().size("online:"+username) > maxSession) {

            if(kickBefore) { //如果踢出后者
                kickoutSessionId = (String) redisTemplate.opsForList().leftPop("online:"+username);
            } else { //否则踢出前者
                kickoutSessionId = (String) redisTemplate.opsForList().rightPop("online:"+username);
            }
            try {
                ShiroRedisManager.ShiroRedisCache cache = (ShiroRedisManager.ShiroRedisCache) sessionManager.getCache(kickoutSessionId.toString());
                if(cache != null) {
                    //设置会话的kickout属性表示踢出了
                    cache.put("kickout", true);
                }
            } catch (Exception e) {//ignore exception
                e.printStackTrace();
            }
        }

        //如果被踢出了，直接退出，重定向到踢出后的地址
        if (((ShiroRedisManager.ShiroRedisCache) sessionManager.getCache(sessionId.toString())).get("kickout") != null) {
            //会话被踢出了
            try {
                subject.logout();
            } catch (Exception e) { //ignore
                e.printStackTrace();
            }
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            Map<String, String> res = new HashMap<>();
            res.put("msg", "你已被顶出，请重新登陆");
            res.put("code", "500");
            String result = JSONUtils.serializeObject(res);
            out.append(result);
            return false;
        }
        return true;
    }

}
