package com.coco.tango.surfing.chat.mq.consumer.support;

import com.coco.tango.surfing.chat.config.RocketMQMessageListenerConfig;
import com.coco.tango.surfing.chat.mq.consumer.TangoChatMessageConsumer;
import com.coco.tango.surfing.common.utils.SpringContextUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.AccessChannel;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * rocketMq 监听启动辅助配置器
 *
 * @author ckli01
 * @date 2019-06-20
 */
@Slf4j
@Component
public class ListenerContainerConfigurationIniter {


    @Autowired
    private SpringContextUtil springContextUtil;

    @Autowired
    private TangoChatMessageConsumer tangoChatMessageConsumer;

    private AtomicLong counter = new AtomicLong(0);

    /**
     * 注册 rocketMq 消费者容器
     *
     * @param config
     */
    public void registerContainer(RocketMQMessageListenerConfig config) {
        validate(config);

        String containerBeanName = String.format("%s_%s", TangoRocketMQListenerContainer.class.getName(),
                counter.incrementAndGet());
        GenericApplicationContext genericApplicationContext = (GenericApplicationContext) springContextUtil.getApplicationContext();

        genericApplicationContext.registerBean(containerBeanName, TangoRocketMQListenerContainer.class,
                () -> createRocketMQListenerContainer(containerBeanName, tangoChatMessageConsumer, config));
        TangoRocketMQListenerContainer container = genericApplicationContext.getBean(containerBeanName,
                TangoRocketMQListenerContainer.class);
        if (!container.isRunning()) {
            try {
                container.start();
            } catch (Exception e) {
                log.error("Started container failed. {}", container, e);
                throw new RuntimeException(e);
            }
        }

        log.info("Register the listener to container, listenerBeanName:{}, containerBeanName:{}", tangoChatMessageConsumer.getClass().getName(), containerBeanName);
    }

    private TangoRocketMQListenerContainer createRocketMQListenerContainer(String name, Object bean, RocketMQMessageListenerConfig config) {
        TangoRocketMQListenerContainer container = new TangoRocketMQListenerContainer();

        String accessChannel = config.getAccessChannel();
        container.setNameServer(config.getNameServer());
        if (!StringUtils.isEmpty(accessChannel)) {
            container.setAccessChannel(AccessChannel.valueOf(accessChannel));
        }
        container.setTopic(config.getTopic());
        container.setConsumerGroup(config.getConsumerGroup());
        container.setRocketMQMessageListenerConfig(config);
        container.setRocketMQListener((RocketMQListener) bean);
        container.setObjectMapper(new ObjectMapper());
        container.setName(name);  // REVIEW ME, use the same clientId or multiple?

        return container;
    }


    private void validate(RocketMQMessageListenerConfig config) {
        if (config.getConsumeMode() == ConsumeMode.ORDERLY &&
                config.getMessageModel() == MessageModel.BROADCASTING) {
            throw new BeanDefinitionValidationException(
                    "Bad RocketMQMessageListenerConfig definition in RocketMQMessageListener, messageModel BROADCASTING does not support ORDERLY message!");
        }
    }


}

    
    
  