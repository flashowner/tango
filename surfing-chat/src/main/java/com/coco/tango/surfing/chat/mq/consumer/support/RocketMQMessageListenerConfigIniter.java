package com.coco.tango.surfing.chat.mq.consumer.support;

import com.coco.tango.surfing.chat.bootstrap.topic.DistributeTopicInitHandler;
import com.coco.tango.surfing.chat.config.RocketMQMessageListenerConfig;
import com.coco.tango.surfing.chat.constant.MqConstants;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * rocketMq 消费者监听配置 启动配置
 *
 * @author ckli01
 * @date 2019-06-20
 */
@Slf4j
public class RocketMQMessageListenerConfigIniter {

    private static Environment environment;

    private static RocketMQMessageListenerConfig rocketMQMessageListenerConfig;


    static {
        environment = SpringContextUtil.getBean(Environment.class);
        if (environment == null) {
            log.error("RocketMQMessageListenerConfigIniter error for StandardEnvironment bean is null");
        }
    }


    /**
     * 获取 rocketMq 监听配置类
     *
     * @return
     */
    public static RocketMQMessageListenerConfig getRocketMQMessageListenerConfig() {
        if (rocketMQMessageListenerConfig == null) {
            synchronized (RocketMQMessageListenerConfigIniter.class) {
                if (rocketMQMessageListenerConfig == null) {
                    rocketMQMessageListenerConfig = rocketMQMessageListenerConfig();
                }
            }
        }
        return rocketMQMessageListenerConfig;
    }

    /**
     * 构建 rocketMq 监听配置类
     *
     * @return
     */
    private static RocketMQMessageListenerConfig rocketMQMessageListenerConfig() {
        String nameServer = environment.getProperty("rocketmq.name-server");
        String accessKey = environment.getProperty("rocketmq.consumer.access-key");
        String secretKey = environment.getProperty("rocketmq.consumer.secret-key");
        String accessChannel = environment.getProperty("rocketmq.access-channel");

        Objects.requireNonNull(nameServer);
        Objects.requireNonNull(accessKey);
        Objects.requireNonNull(secretKey);

        RocketMQMessageListenerConfig config = new RocketMQMessageListenerConfig();
        config.setNameServer(nameServer);
        config.setAccessKey(accessKey);
        config.setSecretKey(secretKey);
        config.setAccessChannel(accessChannel);

        config.setTopic(DistributeTopicInitHandler.TANGO_SURFING_TOPIC);
        config.setConsumerGroup(MqConstants.MQ_TOPIC_CONSUMER_GROUP);

        return config;
    }


}



