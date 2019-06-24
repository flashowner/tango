package com.coco.tango.surfing.chat.service.ws;

import io.netty.channel.Channel;

import java.util.Map;

/**
 * 用户 连接 信息通道处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
public interface WsChannelService {

    /**
     * 登录成功存储到本地缓存
     *
     * @param channel   链接实例
     * @param userCode  用户标识
     */
    void loginWsSuccess(Channel channel, String userCode);

    /**
     * 判断是否存在当前在线用户
     *
     * @param otherOne 用户标识
     * @return 是否存在
     */
    boolean hasOther(String otherOne);

    /**
     * 用户 占用 TOPIC 查询
     *
     * @param otherOne
     * @return
     */
    String otherTopic(String otherOne);

    /**
     * 获取某人的链接实例
     *
     * @param otherOne  用户标识
     * @return  链接实例
     */
    Channel getChannel(String otherOne);

    /**
     * 删除链接与本地存储信息
     *
     * @param channel  链接实例
     */
    void close(Channel channel);

    /**
     * 以服务端API调用向链接发送信息
     *
     * @param channel
     * @param map
     * @return
     */
    boolean sendFromServer(Channel channel, Map<String, String> map);
}
