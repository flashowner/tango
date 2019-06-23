package com.coco.tango.surfing.chat.service;

import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.bean.SendServerVO;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpMessage;

import java.util.Map;

/**
 * 聊天业务 抽象类 处理基类
 *
 * @author ckli01
 * @date 2019-06-12
 */
public abstract class AbstractHandlerBaseService implements HandlerBaseService {

//    /**
//     * HTTP获取在线用户标签列表
//     *
//     * @param channel {@link Channel} 链接实例
//     */
//    public abstract void getList(Channel channel);
//
//    /**
//     * HTTP获取在线用户数
//     *
//     * @param channel {@link Channel} 链接实例
//     */
//    public abstract void getSize(Channel channel);
//
//    /**
//     * HTTP以服务端向指定用户发送通知
//     *
//     * @param channel      {@link Channel} 链接实例
//     * @param sendServerVO {@link SendServerVO} 用户标识
//     */
//    public abstract void sendFromServer(Channel channel, SendServerVO sendServerVO);
//
//    /**
//     * HTTP以服务端处理发送
//     *
//     * @param channel
//     */
//    public abstract void sendInChat(Channel channel, FullHttpMessage msg);
//
//    /**
//     * 用户未找到匹配Uri
//     *
//     * @param channel {@link Channel} 链接实例
//     */
//    public abstract void notFindUri(Channel channel);

    /**
     * 建立聊天关系连接
     *
     * @param channel     链接实例
     * @param chatMessage 数据信息
     * @return 成功失败
     */
    public abstract boolean connOther(Channel channel, ChatMessage chatMessage);

//    /**
//     * 发送给自己
//     *
//     * @param channel 链接实例
//     * @param maps    数据信息
//     */
//    public abstract void sendMeText(Channel channel, Map<String, Object> maps);

    /**
     * 发送给某人
     *
     * @param channel     链接实例
     * @param chatMessage 数据信息
     */
    public abstract void sendToText(Channel channel, ChatMessage chatMessage);

    /**
     * 登录 建立WS 连接
     * @param channel
     * @param chatMessage
     */
    public abstract void login(Channel channel,ChatMessage chatMessage);

    /**
     * mq 接收消息 处理
     * 1. 离线消息 持久化
     * 2. 消息转发
     * @param chatMessage
     */
    public abstract void distributeMessageDeal(ChatMessage chatMessage);

//    /**
//     * 发送给群聊
//     *
//     * @param channel {@link Channel} 链接实例
//     * @param maps    {@link Map} 数据信息
//     */
//    public abstract void sendGroupText(Channel channel, Map<String, Object> maps);
//
//    /**
//     * 登录校验
//     *
//     * @param channel {@link Channel} 链接实例
//     * @param maps    {@link Map} 数据信息
//     */
//    public abstract void verify(Channel channel, Map<String, Object> maps);
//
//    /**
//     * 发送图片给个人
//     *
//     * @param channel {@link Channel} 链接实例
//     * @param maps    {@link Map} 数据信息
//     */
//    public abstract void sendPhotoToMe(Channel channel, Map<String, Object> maps);


}

    
    
  