package com.coco.tango.surfing.chat.mq.producer;

import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 聊天消息 rocketMq
 * 生产者
 *
 * @author ckli01
 * @date 2019-06-20
 */
@Component
public class TangoChatMessageProducer implements MqMessageProducer<ChatMessage> {


    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(String topic, ChatMessage entity) {
        rocketMQTemplate.asyncSend(topic, entity, new SendCallback() {

            public void onSuccess(SendResult var1) {
                // todo 记录消息 是否发送成功 处理
                System.out.printf("async onSucess SendResult=%s %n", var1);
            }

            public void onException(Throwable var1) {
                // todo 记录消息 发送异常原因 处理
                System.out.printf("async onException Throwable=%s %n", var1);
            }

        });

    }

}

    
    
  