package com.coco.tango.surfing.common.redis.biz;

import com.coco.tango.surfing.common.bean.user.TangoUserDTO;
import com.coco.tango.surfing.common.constants.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * token -  用户 信息
 *
 * @author ckli01
 * @date 2019-07-10
 */
@Service
@Slf4j
public class UserTokenRedisCache {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    /**
     * token - 用户信息
     *
     * @param token
     * @param o
     * @return
     */
    public Boolean set(String token, TangoUserDTO o) {
        try {
            redisTemplate.opsForValue().set(tokenKey(token), o, RedisConstant.TANGO_USER_TOKEN_REDIS_EXPIRE, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return true;
    }


    /**
     * 根据 token 获取 用户信息
     *
     * @param token
     * @return
     */
    public TangoUserDTO getByToken(String token) {
        try {
            return (TangoUserDTO) redisTemplate.opsForValue().get(tokenKey(token));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    /**
     * 刷新用户缓存
     *
     * @param token
     */
    public void expire(String token) {
        try {
            redisTemplate.expire(tokenKey(token), RedisConstant.TANGO_USER_TOKEN_REDIS_EXPIRE, TimeUnit.HOURS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * redis 缓存 key
     *
     * @param key
     * @return
     */
    private String tokenKey(String key) {
        return RedisConstant.TANGO_USER_TOKEN_REDIS_PREFIX + key;
    }

}

    
    
  