package com.skcc.redis.sample;


import com.skcc.redis.session.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;


@RestController
@EnableCaching
public class RedisTestController implements Serializable {
    private static final long serialVersionUID = 7312344588204451235L;
    private RedisService redisService;

    @Autowired
    public RedisTestController(
                               RedisService redisService) {
        this.redisService = redisService;
    }

    @RequestMapping(value = "/session/set/{field}/{id}/{pw}", method = RequestMethod.GET)
    public Callable<Object> redisSet(@PathVariable String id, @PathVariable Object field, @PathVariable String pw, HttpSession session) {
        return () -> {
            session.setAttribute("id", id);
            session.setAttribute("pw", pw);
            HashMap m = new HashMap<>();
            m.put("sessionID", session.getId());
            this.redisService.SessionDataSet(session.getId(), field , m);
            return m;
        };
    }


    @RequestMapping(value = "/session/get/{field}", method = RequestMethod.GET)
    public Callable<Object> redisGet(@PathVariable Object field, HttpSession session) {
        return () -> {
           Object m =  this.redisService.SessionDataGet(session.getId(), field);
            return m;
        };
    }

}
