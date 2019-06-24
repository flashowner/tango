package com.coco.tango.surfing.chat.service.ws;


import com.coco.tango.surfing.chat.bean.ChatMessage;
import io.netty.channel.Channel;


/**
 * 聊天业务处理 消息返回
 *
 * @author ckli01
 * @date 2019-06-12
 */
public interface ChatBackService {


    /**
     * 发送给某人的信息，
     * 返回给自己 发送成功
     *
     * @param channel
     * @param chatMessage
     */
    void sendBack(Channel channel, ChatMessage chatMessage);


    /**
     * 消息 发送到接收方
     *
     * @param channel
     * @param chatMessage
     */
    void send(Channel channel, ChatMessage chatMessage);
}
