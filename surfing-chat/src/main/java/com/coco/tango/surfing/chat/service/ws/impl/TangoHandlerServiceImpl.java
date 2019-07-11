package com.coco.tango.surfing.chat.service.ws.impl;

import com.coco.tango.surfing.chat.cache.redis.UserTopicCache;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.enums.ChatMessageCodeEnum;
import com.coco.tango.surfing.chat.mq.producer.TangoChatMessageProducer;
import com.coco.tango.surfing.chat.service.ws.ChatBackService;
import com.coco.tango.surfing.chat.service.ws.HandlerBaseService;
import com.coco.tango.surfing.chat.service.ws.WsChannelService;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessageState;
import com.coco.tango.surfing.core.service.chat.ChatGroupIService;
import com.coco.tango.surfing.core.service.chat.ChatMessageIService;
import com.coco.tango.surfing.core.service.user.TangoUserIService;
import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 聊天业务处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Service
@Slf4j
public class TangoHandlerServiceImpl implements HandlerBaseService {

    @Autowired
    private TangoChatMessageProducer tangoChatMessageProducer;

    @Autowired
    private WsChannelService wsChannelService;

    @Autowired
    private ChatBackService inChatBackMapService;

    @Autowired
    private ChatMessageIService chatMessageIService;

    @Autowired
    private ChatGroupIService chatGroupIService;

    @Autowired
    private UserTopicCache userTopicCache;

    @Autowired
    private TangoUserIService userIService;

    @Override
    public void login(Channel channel, ChatMessage chatMessage) {
        //校验规则，自定义校验规则
//        return check(channel, chatMessage);
        // 反馈服务器接收成功
        inChatBackMapService.sendBack(channel, chatMessage);

        wsChannelService.loginWsSuccess(channel, chatMessage.getSendUserCode());

        try {
            // 获取  离线时间
            List<ChatMessage> chatMessages = chatMessageIService.offLineMessage(
                    chatMessage.getSendUserCode(), ChatMessageCodeEnum.CODE_CLIENT_UNKNOW.getCode());
            List<List<ChatMessage>> lists = Lists.partition(chatMessages, 100);

            for (List<ChatMessage> list : lists) {
                inChatBackMapService.send(channel, list);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    @Override
    public void connOther(Channel channel, ChatMessage chatMessage) {
        //  建立聊天关系 生成groupId
        chatMessage.setGroupId(UUID.randomUUID().toString());
        // 群组持久化
        chatGroupIService.save(chatMessage);

        inChatBackMapService.sendBack(channel, chatMessage, ChatMessageConstants.CONN_OTHER);
    }

    @Override
    public void sendToText(Channel channel, ChatMessage chatMessage) {
        // 持久化消息
        chatMessageIService.save(chatMessage);

        // 返回消息状态
        inChatBackMapService.sendBack(channel, chatMessage);

        sendToText(chatMessage);
    }

    /**
     * 根据groupId 获取 组内成员 然后判断是否在本地
     * 不在本地获取 这些用户 的topic 发送mq 消息
     * recvUserCode 用 ; 隔开
     *
     * @param chatMessage 数据信息
     */
    @Override
    public void sendToText(ChatMessage chatMessage) {
        //  根据groupId 获取 组内成员  然后获取 topic 然后发送MQ  recvUserCode 多个用 ； 隔开
        List<String> userCodes = chatGroupIService.searchByGroupId(chatMessage.getGroupId());

        List<String> otherUserCodes = Lists.newArrayList();
        List<ChatMessageState> chatMessageStates = Lists.newArrayList();
        for (String code : userCodes) {
            if (code.equals(chatMessage.getSendUserCode())) {
                continue;
            }
            ChatMessageState chatMessageState = new ChatMessageState();
            chatMessageState.setChatMessageId(chatMessage.getId());
            chatMessageState.setCode(ChatMessageCodeEnum.CODE_CLIENT_UNKNOW.getCode());
            chatMessageState.setUserCode(code);

            chatMessageStates.add(chatMessageState);

            // 本地用户 直接转发给用户
            if (wsChannelService.hasOther(code)) {

                //发送给对方--在线
                Channel other = wsChannelService.getChannel(code);
                if (other != null) {
                    // 转到 接收方
                    inChatBackMapService.send(other, chatMessage);
                }
            } else {
                otherUserCodes.add(code);
            }
        }

        // todo 异步保存
        chatMessageIService.batchSave(chatMessageStates);

        // 判断用户 是否在 别的机器上
        if (!CollectionUtils.isEmpty(otherUserCodes)) {
            List<String> userTopic = userTopicCache.multiGet(otherUserCodes, chatMessage.getSendUserCode());
            Set<String> userTopicSet = new HashSet<>(userTopic);

            String recvCodes = StringUtils.join(userTopic, ";");

            chatMessage.setRecvUserCode(recvCodes);
            if (!CollectionUtils.isEmpty(userTopicSet)) {
                // 发送MQ 消息
                for (String topic : userTopicSet) {
                    // todo
//                    tangoChatMessageProducer.send(topic, chatMessage);
                }
            }
        }
    }

    @Override
    public void distributeMessageDeal(ChatMessage chatMessage) {
        //  消息 getRecvUserCode 根据 ；分割
        if (StringUtils.isNotEmpty(chatMessage.getRecvUserCode())) {
            String[] recvUsers = chatMessage.getRecvUserCode().split(";");

            for (String recvUserCode : recvUsers) {

                if (wsChannelService.hasOther(recvUserCode)) {
                    //发送给对方--在线
                    Channel other = wsChannelService.getChannel(recvUserCode);
                    if (other != null) {
                        // 转到 接收方
                        inChatBackMapService.send(other, chatMessage);
                    }
                }
            }

        }
    }

    @Override
    public void changeMessageState(ChatMessage chatMessage) {
        if (StringUtils.isEmpty(chatMessage.getCode())) {
            chatMessage.setCode(ChatMessageCodeEnum.CODE_CLIENT_SUCCESS.getCode());
        }
        chatMessageIService.updateMessageStateSuccess(chatMessage);
    }

    @Override
    public void close(Channel channel) {
        wsChannelService.close(channel);
    }
}
