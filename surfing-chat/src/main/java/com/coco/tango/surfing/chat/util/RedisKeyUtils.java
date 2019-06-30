package com.coco.tango.surfing.chat.util;

import com.coco.tango.surfing.chat.constant.RedisConstant;

/**
 * redis key 生成
 *
 * @author ckli01
 * @date 2019-06-26
 */
public class RedisKeyUtils {


    /**
     * 本机占用 TOPIC
     *
     * @param topic
     * @return
     */
    public static String hostTopicKey(String topic) {
        return RedisConstant.REDIS_MQ_TOPIC_HOST_USE_PREFIX + topic;
    }


    /**
     * 用户占用 TOPIC
     * @param userCode
     * @return
     */
    public static String userTopicKey(String userCode) {
        return RedisConstant.REDIS_MQ_USER_TOPIC_PREFIX + userCode;
    }


}

    
    
  