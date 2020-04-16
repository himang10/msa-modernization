package com.skcc.redis.session.service;

import com.skcc.redis.session.config.RedisConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class RedisService implements Serializable {
    private static final long serailVersionUID = 7312344588204451235L;
    private RedisConfiguration RedisConf;



    @Autowired
    public RedisService(RedisConfiguration redisConf) {
        this.RedisConf = redisConf;
    }

    public void SessionDataSet(String key, Object field, Object value) {
        this.RedisConf.redisTemplate().opsForHash().put(this.RedisConf.namespace + ":" + key,field,value);
    }

    public Object SessionDataGet(String key, Object field) {
        return this.RedisConf.redisTemplate().opsForHash().get(this.RedisConf.namespace + ":" + key, field);
    }
}
