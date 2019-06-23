package com.coco.tango.surfing.chat.constant;

/**
 * redis 使用key 常量
 *
 * @author ckli01
 * @date 2019-06-18
 */
public class RedisConstant {


    /**
     * mq topic tag 初始化分配锁key
     */
    public static final String TOPIC_TAG_INIT_KEY = "surfing.topic.tag.init";

    /**
     * mq topic 占用 前缀key
     */
    public static final String REDIS_MQ_TOPIC_HOST_USE_PREFIX = "tsht-";

    /**
     * mq  user  topic 占用 前缀key
     */
    public static final String REDIS_MQ_USER_TOPIC_PREFIX = "tsut-";


    /**
     * 本机占用 mq  topic  redis key 过期时间
     * 单位 秒
     */
    public static final long REDIS_MQ_TOPIC_HOST_TAG_EXPIRE_TIME = 30;

    /**
     * 连接到本机上的 user topic   redis key 过期时间
     * 单位 小时
     */
    public static final long REDIS_USER_TOPIC_EXPIRE_TIME = 6;

}

    
    
  