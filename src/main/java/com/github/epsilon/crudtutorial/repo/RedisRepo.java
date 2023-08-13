package com.github.epsilon.crudtutorial.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisRepo {
    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisRepo(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    public String getStatus(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void setStatus(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }


    public void setStatusWithExpiry(String key, String value, Duration expiry) {
        stringRedisTemplate.opsForValue().set(key, value, expiry);
    }
}

