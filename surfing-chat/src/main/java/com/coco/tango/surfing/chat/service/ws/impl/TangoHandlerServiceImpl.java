package com.coco.tango.surfing.chat.service.ws.impl;

import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.mq.producer.TangoChatMessageProducer;
import com.coco.tango.surfing.chat.service.ws.ChatBackService;
import com.coco.tango.surfing.chat.service.ws.HandlerBaseService;
import com.coco.tango.surfing.chat.service.ws.WsChannelService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 聊天业务处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Service
public class TangoHandlerServiceImpl implements HandlerBaseService {

    @Autowired
    private TangoChatMessageProducer tangoChatMessageProducer;

    @Autowired
    private WsChannelService wsChannelService;

    @Autowired
    private ChatBackService inChatBackMapService;

    @Override
    public void login(Channel channel, ChatMessage chatMessage) {
        //校验规则，自定义校验规则
//        return check(channel, chatMessage);
        // 反馈服务器接收成功
        inChatBackMapService.sendBack(channel, chatMessage);
        wsChannelService.loginWsSuccess(channel, chatMessage.getSendUserCode());
        // todo 接收离线消息
    }


    @Override
    public boolean connOther(Channel channel, ChatMessage chatMessage) {
        // todo 建立聊天关系
        return false;
    }

    @Override
    public void sendToText(Channel channel, ChatMessage chatMessage) {
        sendToText(chatMessage);
        // 反馈服务器接收成功
        inChatBackMapService.sendBack(channel, chatMessage);
    }

    @Override
    public void sendToText(ChatMessage chatMessage) {
        if (wsChannelService.hasOther(chatMessage.getRecvUserCode())) {
            //发送给对方--在线
            Channel other = wsChannelService.getChannel(chatMessage.getRecvUserCode());
            if (other == null) {
                // todo 直接 持久化
            } else {
                // 转到 接收方
                inChatBackMapService.send(other, chatMessage);
            }
        } else {
            // 转 mq 消息
            String topic = wsChannelService.otherTopic(chatMessage.getRecvUserCode());
            if (!StringUtils.isEmpty(topic)) {
                tangoChatMessageProducer.send(topic, chatMessage);
            } else {
                // todo 直接 持久化
            }
        }
    }

    @Override
    public void distributeMessageDeal(ChatMessage chatMessage) {
        if (wsChannelService.hasOther(chatMessage.getRecvUserCode())) {
            //发送给对方--在线
            Channel other = wsChannelService.getChannel(chatMessage.getRecvUserCode());
            if (other == null) {
                // todo 持久化
            } else {
                // 转到 接收方
                inChatBackMapService.send(other, chatMessage);
            }
        } else {
            // todo 持久化
        }
    }

    @Override
    public void close(Channel channel) {
        wsChannelService.close(channel);
    }
}
