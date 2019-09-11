package com.front.component;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

import javax.security.auth.Destroyable;
import java.util.Collection;
import java.util.concurrent.Callable;

/***
 **@project: cli
 **@description:
 **@Author: twj
 **@Date: 2019/09/11
 **/
public class SecurityRedisUserCacheManager implements Destroyable, CacheManager {


    private RedisCacheManager cacheManager;

    public RedisCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public Cache getCache(String s) {
        return cacheManager.getCache("Authentication::"+s);
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheManager.getCacheNames();
    }

    class SecurityRedisUserCache extends RedisCache implements UserCache {

        private Cache cache;

        protected SecurityRedisUserCache(String name, RedisCacheWriter cacheWriter, RedisCacheConfiguration cacheConfig) {
            super(name, cacheWriter, cacheConfig);
        }


        @Override
        public UserDetails getUserFromCache(String s) {
            if (s == null) return null;
            cache = cacheManager.getCache("Authentication");
            return (UserDetails) cache.get(s).get();
        }

        @Override
        public void putUserInCache(UserDetails userDetails) {
            cache.put(userDetails.getUsername(), userDetails);
        }

        @Override
        public void removeUserFromCache(String s) {
            Cache cache = cacheManager.getCache("Authentication" );
            cache.clear();
        }

        @Override
        public String getName() {
            return cache.getName();
        }

        @Override
        public ValueWrapper get(Object o) {
            return cache.get(o);
        }

        @Override
        public <T> T get(Object o, Class<T> aClass) {
            return cache.get(o, aClass);
        }

        @Override
        public <T> T get(Object o, Callable<T> callable) {
            return cache.get(o, callable);
        }

        @Override
        public void put(Object o, Object o1) {
            cache.put(o, o1);
        }

        @Override
        public ValueWrapper putIfAbsent(Object o, Object o1) {
            return cache.putIfAbsent(o, o1);
        }

        @Override
        public void evict(Object o) {
            cache.evict(o);
        }

        @Override
        public void clear() {
            cache.clear();
        }

        @Override
        public String toString() {
            return "SecurityRedisCache{" +
                    "cache=" + cache +
                    '}';
        }

    }
}