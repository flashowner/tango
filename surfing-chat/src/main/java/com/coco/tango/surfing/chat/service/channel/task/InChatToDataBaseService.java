package com.coco.tango.surfing.chat.service.channel.task;

import com.coco.tango.surfing.chat.bean.ChatMessage;

/**
 * 写消息 基础服务
 *
 * @author ckli01
 * @date 2019-06-13
 */
public interface InChatToDataBaseService {


    /**
     * 写消息 到 通道
     * @param message
     * @return
     */
    Boolean writeMessage(ChatMessage message);


}
