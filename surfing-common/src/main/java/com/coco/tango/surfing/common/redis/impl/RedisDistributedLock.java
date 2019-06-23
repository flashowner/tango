package com.coco.tango.surfing.common.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁
 *
 * @author ckli01
 * @date 2018/9/18
 */
@Component
public class RedisDistributedLock extends AbstractRedisDistributedLock {


    @Autowired
    @Override
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        super.setRedisTemplate(redisTemplate);
    }
}
