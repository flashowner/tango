package com.coco.tango.surfing.chat.service.ws;


import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.enums.ChatMessageCodeEnum;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.List;


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
     * 发送给某人的信息，
     * 返回给自己 发送成功
     * 含消息类型
     * @param channel
     * @param chatMessage
     * @param type
     */
    void sendBack(Channel channel, ChatMessage chatMessage, String type);


    /**
     * 消息 发送到接收方
     *
     * @param channel
     * @param chatMessage
     */
    void send(Channel channel, ChatMessage chatMessage);


    /**
     * 消息 发送到接收方
     *
     * @param channel
     * @param chatMessages
     */
    void send(Channel channel, List<ChatMessage> chatMessages);


}
