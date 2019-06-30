package com.coco.tango.surfing.chat.service.ws;

import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import io.netty.channel.Channel;

/**
 * 聊天业务处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
public interface HandlerBaseService {

    /**
     * 建立聊天关系连接
     *
     * @param channel     链接实例
     * @param chatMessage 数据信息
     * @return 成功失败
     */
    public void connOther(Channel channel, ChatMessage chatMessage);


    /**
     * 发送给某人
     *
     * @param channel     链接实例
     * @param chatMessage 数据信息
     */
    public void sendToText(Channel channel, ChatMessage chatMessage);


    /**
     * 发送给某人
     *
     * @param chatMessage 数据信息
     */
    public void sendToText(ChatMessage chatMessage);


    /**
     * 登录 建立WS 连接
     *
     * @param channel
     * @param chatMessage
     */
    public void login(Channel channel, ChatMessage chatMessage);

    /**
     * mq 接收消息 处理
     * 1. 离线消息 持久化
     * 2. 消息转发
     *
     * @param chatMessage
     */
    public void distributeMessageDeal(ChatMessage chatMessage);


    /**
     * 关闭通道
     *
     * @param channel
     */
    void close(Channel channel);

    /**
     * 改变消息状态
     * @param chatMessage
     */
    void changeMessageState(ChatMessage chatMessage);
}
