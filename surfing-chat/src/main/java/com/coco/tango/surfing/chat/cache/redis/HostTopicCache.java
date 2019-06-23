package com.coco.tango.surfing.chat.cache.redis;

import com.coco.tango.surfing.chat.bootstrap.topic.DistributeTopicInitHandler;
import com.coco.tango.surfing.chat.constant.RedisConstant;
import com.coco.tango.surfing.common.redis.impl.RedisBaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * MQ TOPIC 占用 关系缓存
 *
 * @author ckli01
 * @date 2019-06-19
 */
@Service
public class HostTopicCache extends RedisBaseServiceImpl<String, String, String> {


    /**
     * 更新 MQ TOPIC 占用 缓存 时间
     */
    public void expireHostTagKey() {
        super.getRedisTemplate().opsForValue().set(DistributeTopicInitHandler.getHostTopicCacheKey(), "LCK", RedisConstant.REDIS_MQ_TOPIC_HOST_TAG_EXPIRE_TIME, TimeUnit.SECONDS);
    }


}

    
    
  