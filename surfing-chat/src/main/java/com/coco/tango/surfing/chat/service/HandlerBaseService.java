package com.coco.tango.surfing.chat.service;

import io.netty.channel.Channel;

/**
 * 聊天业务处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
public interface HandlerBaseService {

    /**
     * 关闭通道
     *
     * @param channel
     */
    void close(Channel channel);

}
