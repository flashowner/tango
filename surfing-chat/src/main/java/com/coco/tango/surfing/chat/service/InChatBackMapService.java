package com.coco.tango.surfing.chat.service;


import com.coco.tango.surfing.chat.bean.ChatMessage;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * 聊天业务处理 消息返回
 *
 * @author ckli01
 * @date 2019-06-12
 */
public interface InChatBackMapService {

//    /**
//     * 登录成功返回信息
//     *
//     * @return {@link Map} Json
//     */
//    Map<String, String> loginSuccess();
//
//    /**
//     * 登录失败返回信息
//     *
//     * @return {@link Map} Json
//     */
//    Map<String, String> loginError();
//
//    /**
//     * 发送给自己
//     *
//     * @param value {@link String} 通讯消息
//     * @return {@link Map} Json
//     */
//    Map<String, String> sendMe(String value);

    /**
     * 发送给某人的信息，
     * 返回给自己 发送成功
     *
     * @param channel
     * @param chatMessage
     */
    void sendBack(Channel channel, ChatMessage chatMessage);

//    /**
//     * 某人接收到他人发送给他的消息
//     *
//     * @param me    {@link String} 发送人的标签
//     * @param value {@link String} 通讯消息
//     * @return
//     */
//    Map<String, String> getMsg(String me, String value);
//
//    /**
//     * 发送消息到群里
//     *
//     * @param me      {@link String} 发送人的标签
//     * @param value   {@link String} 通讯消息
//     * @param groupId {@link String} 群聊Id
//     * @return
//     */
//    Map<String, String> sendGroup(String me, String value, String groupId);

    /**
     * 消息 发送到接收方
     *
     * @param channel
     * @param chatMessage
     */
    void send(Channel channel, ChatMessage chatMessage);
}
