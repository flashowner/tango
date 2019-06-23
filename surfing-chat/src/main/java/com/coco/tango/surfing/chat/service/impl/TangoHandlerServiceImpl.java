package com.coco.tango.surfing.chat.service.impl;

import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.mq.producer.TangoChatMessageProducer;
import com.coco.tango.surfing.chat.service.AbstractHandlerBaseService;
import com.coco.tango.surfing.chat.service.InChatBackMapService;
import com.coco.tango.surfing.chat.service.channel.ws.WsChannelService;
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
public class TangoHandlerServiceImpl extends AbstractHandlerBaseService {

//    private final InChatVerifyService inChatVerifyService;
//
//    private final InChatBackMapService inChatBackMapService = new InChatBackMapServiceImpl();
//
//    private final HttpChannelService httpChannelService = new HttpChannelServiceImpl();
//

    @Autowired
    private TangoChatMessageProducer tangoChatMessageProducer;


    @Autowired
    private WsChannelService wsChannelService;

    @Autowired
    private InChatBackMapService inChatBackMapService;

//    @Autowired
//    private DataAsynchronousTask dataAsynchronousTask;

//    public HandlerServiceImpl(DataAsynchronousTask dataAsynchronousTask,InChatVerifyService inChatVerifyService) {
//        this.dataAsynchronousTask = dataAsynchronousTask;
//        this.inChatVerifyService = inChatVerifyService;
//    }
//
//
//    @Override
//    public void getList(Channel channel) {
//        httpChannelService.getList(channel);
//    }
//
//    @Override
//    public void getSize(Channel channel) {
//        httpChannelService.getSize(channel);
//    }
//
//    @Override
//    public void sendFromServer(Channel channel, SendServerVO serverVO) {
//        httpChannelService.sendFromServer(channel,serverVO);
//    }
//
//    @Override
//    public void sendInChat(Channel channel, FullHttpMessage msg) {
//        System.out.println(msg);
//        String content = msg.content().toString(CharsetUtil.UTF_8);
//        Gson gson = new Gson();
//        SendInChat sendInChat = gson.fromJson(content,SendInChat.class);
//        httpChannelService.sendByInChat(channel,sendInChat);
//    }
//
//    @Override
//    public void notFindUri(Channel channel) {
//        httpChannelService.notFindUri(channel);
//    }

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

//    @Override
//    public void sendMeText(Channel channel, Map<String, Object> maps) {
//        channel.writeAndFlush(new TextWebSocketFrame(
//                JSONObject.toJSONString(inChatBackMapService.sendMe((String) maps.get(ChatMessageConstants.VALUE))))
//        );
//        try {
//            dataAsynchronousTask.writeData(maps);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void sendToText(Channel channel, ChatMessage chatMessage) {
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
        // 反馈服务器接收成功
        inChatBackMapService.sendBack(channel, chatMessage);
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

    //    @Override
//    public void sendGroupText(Channel channel, Map<String, Object> maps) {
//        Gson gson = new Gson();
//        String groupId = (String) maps.get(Constans.GROUPID);
//        String token = (String) maps.get(Constans.TOKEN);
//        String value = (String) maps.get(Constans.VALUE);
//        List<String> no_online = new ArrayList<>();
//        JSONArray array = inChatVerifyService.getArrayByGroupId(groupId);
//        channel.writeAndFlush(new TextWebSocketFrame(
//                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
//        for (Object item:array) {
//            if (!token.equals(item)){
//                if (websocketChannelService.hasOther((String) item)){
//                    Channel other = websocketChannelService.getChannel((String) item);
//                    if (other == null){
//                        //转http分布式
//                        httpChannelService.sendInChat((String) item,inChatBackMapService.sendGroup(token,value,groupId));
//                    }else{
//                        other.writeAndFlush(new TextWebSocketFrame(
//                                gson.toJson(inChatBackMapService.sendGroup(token,value,groupId))));
//                    }
//                }else{
//                    no_online.add((String) item);
//                }
//            }
//        }
//        maps.put(Constans.ONLINE_GROUP,no_online);
//        try {
//            dataAsynchronousTask.writeData(maps);
//        } catch (Exception e) {
//            return;
//        }
//    }
//
//    @Override
//    public void verify(Channel channel, Map<String, Object> maps) {
//        Gson gson = new Gson();
//        String token = (String) maps.get(Constans.TOKEN);
//        System.out.println(token);
//        if (inChatVerifyService.verifyToken(token)){
//            return;
//        }else{
//            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
//            close(channel);
//        }
//    }
//
//    @Override
//    public void sendPhotoToMe(Channel channel, Map<String, Object> maps) {
//        Gson gson = new Gson();
//        System.out.println(maps.get(Constans.VALUE));
//        channel.writeAndFlush(new TextWebSocketFrame(
//                gson.toJson(inChatBackMapService.sendMe((String) maps.get(Constans.VALUE)))));
//        try {
//            dataAsynchronousTask.writeData(maps);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private Boolean check(Channel channel, Map<String, Object> maps) {
//        Gson gson = new Gson();
//        String token = (String) maps.get(Constans.TOKEN);
//        if (inChatVerifyService.verifyToken(token)){
//            channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginSuccess())));
//            websocketChannelService.loginWsSuccess(channel,token);
//            return true;
//        }
//        channel.writeAndFlush(new TextWebSocketFrame(gson.toJson(inChatBackMapService.loginError())));
//        close(channel);
//        return false;
//    }

    @Override
    public void close(Channel channel) {
        wsChannelService.close(channel);
    }
}
