package com.skcc.redis.sample;


import com.skcc.redis.cache.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.concurrent.Callable;


@RestController
@EnableCaching
public class RedisTestController implements Serializable {
    private static final long serialVersionUID = 7312344588204451235L;

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    public RedisTestController(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    @Autowired
    RedisCacheManager cacheManager;

    /* Cache Test */
    @Cacheable(value = "redisCache", key = "#key")
    @RequestMapping(value = "/cache/set/{key}/{value}", method = RequestMethod.GET)
    public Object redisCacheSet(@PathVariable String value, @PathVariable String key) {
        return this.cacheManager.getCacheNames().toString();
    }


    @CacheEvict(value = "redisCache", key = "#key")
    @RequestMapping(value = "/cache/del/{key}", method = RequestMethod.GET)
    public Callable<Object> redisCacheEvict(@PathVariable String key) {
        return () -> {
            return "cache eviction successfully!";
        };
    }

    @RequestMapping(value = "/cache/check/{key}", method = RequestMethod.GET)
    public Callable<Object> redisCacheCheck(@PathVariable String key) {
        return () -> {
            if (StringUtils.isEmpty(cacheManager.getCache("redisCache").get(key).get()))
                return "Cache is empty";
            else return cacheManager.getCache("redisCache").get(key).get();
        };
    }
}
