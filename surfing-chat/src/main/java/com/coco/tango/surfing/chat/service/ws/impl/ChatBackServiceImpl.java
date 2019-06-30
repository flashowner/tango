package com.coco.tango.surfing.chat.service.ws.impl;


import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.enums.ChatMessageCodeEnum;
import com.coco.tango.surfing.chat.service.ws.ChatBackService;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * 聊天业务处理 消息返回处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Service
public class ChatBackServiceImpl implements ChatBackService {


    @Override
    public void send(Channel channel, ChatMessage chatMessage) {
        channel.writeAndFlush(new TextWebSocketFrame(
                JSONObject.toJSONString(chatMessage)));
    }


    @Override
    public void sendBack(Channel channel, ChatMessage chatMessage) {
        sendBack(channel, chatMessage, null);
    }


    @Override
    public void sendBack(Channel channel, ChatMessage chatMessage, String type) {
        ChatMessage chatMessageBack = new ChatMessage();

        chatMessageBack.setCode(ChatMessageCodeEnum.CODE_SERVER_SUCCESS.getCode());

        chatMessageBack.setGroupId(chatMessage.getGroupId());
        chatMessageBack.setId(chatMessage.getId());

        chatMessageBack.setSendUserCode(chatMessage.getSendUserCode());

        chatMessageBack.setType(StringUtils.isEmpty(type) ? ChatMessageConstants.SYSTEM_BACK_CLIENT : type);

        channel.writeAndFlush(new TextWebSocketFrame(
                JSONObject.toJSONString(chatMessageBack)));
    }


    @Override
    public void send(Channel channel, List<ChatMessage> chatMessages) {
        channel.writeAndFlush(new TextWebSocketFrame(
                JSONObject.toJSONString(chatMessages)));
    }
}
