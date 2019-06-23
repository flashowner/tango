package com.coco.tango.surfing.chat.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.service.InChatBackMapService;
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
public class InChatBackMapServiceImpl implements InChatBackMapService {


//    public Map<String, String> loginSuccess() {
//        Map<String, String> backMap = new HashMap<String, String>();
//        backMap.put(ChatMessageConstants.TYPE, ChatMessageConstants.LOGIN);
//        backMap.put(ChatMessageConstants.SUCCESS, ChatMessageConstants.TRUE);
//        return backMap;
//    }
//
//
//    public Map<String, String> loginError() {
//        Map<String, String> backMap = new HashMap<String, String>();
//        backMap.put(ChatMessageConstants.TYPE, ChatMessageConstants.LOGIN);
//        backMap.put(ChatMessageConstants.SUCCESS, ChatMessageConstants.FALSE);
//        return backMap;
//    }
//
//
//    public Map<String, String> sendMe(String value) {
//        Map<String, String> backMap = new HashMap<String, String>();
//        backMap.put(ChatMessageConstants.TYPE, ChatMessageConstants.SENDME);
//        backMap.put(ChatMessageConstants.VALUE, value);
//        return backMap;
//    }


//    public Map<String, String> sendBack(String otherOne, String value) {
//        Map<String,String> backMap = new HashMap<String,String>();
//        backMap.put(ChatMessageTypeConstants.TYPE, ChatMessageTypeConstants.SENDTO);
//        backMap.put(ChatMessageTypeConstants.VALUE,value);
//        backMap.put(ChatMessageTypeConstants.ONE,otherOne);
//        return backMap;
//    }
//
//
//    public Map<String, String> getMsg(String token, String value) {
//        Map<String, String> backMap = new HashMap<String, String>();
//        backMap.put(ChatMessageConstants.TYPE, ChatMessageConstants.SENDTO);
//        backMap.put(ChatMessageConstants.FROM, token);
//        backMap.put(ChatMessageConstants.VALUE, value);
//        return backMap;
//    }
//
//
//    public Map<String, String> sendGroup(String token, String value, String groupId) {
//        Map<String, String> backMap = new HashMap<String, String>();
//        backMap.put(ChatMessageConstants.TYPE, ChatMessageConstants.SENDGROUP);
//        backMap.put(ChatMessageConstants.FROM, token);
//        backMap.put(ChatMessageConstants.VALUE, value);
//        backMap.put(ChatMessageConstants.GROUPID, groupId);
//        return backMap;
//    }


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
