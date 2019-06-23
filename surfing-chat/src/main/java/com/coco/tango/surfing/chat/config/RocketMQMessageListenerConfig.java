package com.coco.tango.surfing.chat.config;

import lombok.Data;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.SelectorType;


/**
 * rocketMq 消费者监听配置
 *
 * @author ckli01
 * @date 2019-06-20
 */
@Data
public class RocketMQMessageListenerConfig {

//    String NAME_SERVER_PLACEHOLDER = "${rocketmq.name-server:}";
//    String ACCESS_KEY_PLACEHOLDER = "${rocketmq.consumer.access-key:}";
//    String SECRET_KEY_PLACEHOLDER = "${rocketmq.consumer.secret-key:}";
//    String ACCESS_CHANNEL_PLACEHOLDER = "${rocketmq.access-channel:}";

    /**
     * Consumers of the same role is required to have exactly same subscriptions and consumerGroup to correctly achieve
     * load balance. It's required and needs to be globally unique.
     * <p>
     * <p>
     * See <a href="http://rocketmq.apache.org/docs/core-concept/">here</a> for further discussion.
     */
    private String consumerGroup;

    /**
     * Topic name.
     */
    private String topic;

    /**
     * Control how to selector message.
     *
     * @see SelectorType
     */
    private SelectorType selectorType = SelectorType.TAG;

    /**
     * Control which message can be select. Grammar please see {@link SelectorType#TAG} and {@link SelectorType#SQL92}
     */
    private String selectorExpression = "*";

    /**
     * Control consume mode, you can choice receive message concurrently or orderly.
     */
    private ConsumeMode consumeMode = ConsumeMode.CONCURRENTLY;

    /**
     * Control message mode, if you want all subscribers receive message all message, broadcasting is a good choice.
     */
    private MessageModel messageModel = MessageModel.CLUSTERING;

    /**
     * Max consumer thread number.
     */
    private Integer consumeThreadMax = 64;

    /**
     * Max consumer timeout, default 30s.
     */
    private Long consumeTimeout = 30000L;

    /**
     * The property of "access-key".
     */
    private String accessKey;

    /**
     * The property of "secret-key".
     */
    private String secretKey;

    /**
     * Switch flag instance for message trace.
     */
    private boolean enableMsgTrace = true;

    /**
     * The name value of message trace topic.If you don't config,you can use the default trace topic name.
     */
    private String customizedTraceTopic;

    /**
     * The property of "name-server".
     */
    private String nameServer;

    /**
     * The property of "access-channel".
     */
    private String accessChannel;


}

    
    
  