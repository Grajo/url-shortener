package com.example.urlshortener.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {
    @Autowired private StringRedisTemplate redisTemplate;

    public void save(String key, String realUrl) {
        redisTemplate.opsForValue().set(key, realUrl);
    }

    public String findUrl(String key) {
        return redisTemplate.hasKey(key) ? redisTemplate.opsForValue().get(key) : null;
    }
}
