package com.coco.tango.surfing.chat.service.ws.impl;


import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.service.ws.ChatBackService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Service;


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
        ChatMessage chatMessageBack = new ChatMessage();

        chatMessageBack.setCode(ChatMessageConstants.CODE_SUCCESS);
        chatMessageBack.setGroupId(chatMessage.getGroupId());
        chatMessageBack.setId(chatMessage.getId());
        chatMessageBack.setType(ChatMessageConstants.SYSTEM_BACK_CLIENT);

        channel.writeAndFlush(new TextWebSocketFrame(
                JSONObject.toJSONString(chatMessageBack)));
    }
}
