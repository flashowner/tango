package com.coco.tango.surfing.chat.mq.producer;

/**
 * mq 消息 生产者
 *
 * @author ckli01
 * @date 2019-06-20
 */
public interface MqMessageProducer<T> {


    /**
     * 发送消息
     *
     * @param entity
     */
    void send(String topic, T entity);


}
