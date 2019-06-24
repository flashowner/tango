package com.coco.tango.surfing.chat.cache.redis;

import com.coco.tango.surfing.chat.bootstrap.init.DistributeTopicInitHandler;
import com.coco.tango.surfing.chat.constant.RedisConstant;
import com.coco.tango.surfing.common.redis.impl.RedisBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 保存用户 占用 TOPIC 缓存
 *
 * @author ckli01
 * @date 2019-06-21
 */
@Service
public class UserTopicCache extends RedisBaseServiceImpl<String, String, String> {


    /**
     * 设置 用户 占用 TOPIC redis 缓存
     *
     * @param userCode
     */
    public void userTopic(String userCode) {
        super.getRedisTemplate().opsForValue().set(RedisConstant.REDIS_MQ_USER_TOPIC_PREFIX + userCode, DistributeTopicInitHandler.TANGO_SURFING_TOPIC, RedisConstant.REDIS_USER_TOPIC_EXPIRE_TIME, TimeUnit.HOURS);
    }

    @Override
    public String get(String key) {
        return super.get(RedisConstant.REDIS_MQ_USER_TOPIC_PREFIX + key);
    }
}

    
    
  