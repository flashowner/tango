package com.coco.tango.surfing.chat.bootstrap.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.constant.LogConstant;
import com.coco.tango.surfing.chat.constant.NotInChatConstant;
import com.coco.tango.surfing.chat.exception.NoFindHandlerException;
import com.coco.tango.surfing.chat.service.AbstractHandlerBaseService;
import com.coco.tango.surfing.chat.service.HandlerBaseService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 消息处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
@ChannelHandler.Sharable
@Slf4j
public class DefaultHandler extends AbstractHandler {


    private HandlerBaseService handlerBaseService;

    public DefaultHandler(HandlerBaseService handlerBaseService) {
        this.handlerBaseService = handlerBaseService;
    }

    @Override
    protected void webDoMessage(ChannelHandlerContext ctx, WebSocketFrame msg) {
        Channel channel = ctx.channel();
//        if (handlerBaseService instanceof AbstractHandlerBaseService) {
//            handlerBaseService.close();
//        } else {
//            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
//        }
        if (msg instanceof BinaryWebSocketFrame) {
            //TODO 实现图片处理

            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) msg;
            ByteBuf content = binaryWebSocketFrame.content();
            log.info("├ [二进制数据]:{}", content);


            final int length = content.readableBytes();
            final byte[] array = new byte[length];
            content.readBytes(array);

            String str= new String(array);


            System.out.println(1);

//            MessagePack messagePack = new MessagePack();
//            WebSocketMessageEntity webSocketMessageEntity = messagePack.read(array, WebSocketMessageEntity.class);
//            LOGGER.info("├ [解码数据]: {}", webSocketMessageEntity);
//            WebSocketUsers.sendMessageToUser(webSocketMessageEntity.getAcceptName(), webSocketMessageEntity.getContent());

        }
    }

    @Override
    protected void httpDoMessage(ChannelHandlerContext ctx, FullHttpRequest msg) {
        Channel channel = ctx.channel();
        if (handlerBaseService instanceof AbstractHandlerBaseService) {
            AbstractHandlerBaseService handler = (AbstractHandlerBaseService) handlerBaseService;
//            switch (HttpRequestDealUtil.checkType(msg)) {
//                case HttpConstant.GETSIZE:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETSIZE);
//                    handler.getSize(channel);
//                    break;
//                case HttpConstant.SENDFROMSERVER:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDFROMSERVER);
//                    SendServerVO serverVO = null;
//                    try {
//                        serverVO = HttpRequestDealUtil.getToken(msg);
//                    } catch (UnsupportedEncodingException e) {
//                        log.error(e.getMessage());
//                    }
//                    handler.sendFromServer(channel, serverVO);
//                    break;
//                case HttpConstant.GETLIST:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_GETLIST);
//                    handler.getList(channel);
//                    break;
//                case HttpConstant.SENDINCHAT:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDINCHAT);
//                    handler.sendInChat(channel, msg);
//                    break;
//                case HttpConstant.NOTFINDURI:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_NOTFINDURI);
//                    handler.notFindUri(channel);
//                    break;
//                default:
//                    System.out.println("为匹配" + msg);
//                    break;
//            }
        } else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }

    }

    @Override
    protected void textDoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        if (handlerBaseService instanceof AbstractHandlerBaseService) {
            AbstractHandlerBaseService handler = (AbstractHandlerBaseService) handlerBaseService;

            ChatMessage chatMessage = JSONObject.parseObject(msg.text(), ChatMessage.class);

            if (!validateChatMessage(channel, chatMessage)) {
                return;
            }
            chatMessage.setTime(new Date());
            // 不同类型消息 不同 处理
            switch (chatMessage.getType()) {
                case ChatMessageConstants.LOGIN:
                    handler.login(channel, chatMessage);
                    break;
                // 建立交流
                case ChatMessageConstants.CONN_OTHER:
                    handler.connOther(channel, chatMessage);
                    break;
                // 文字交流
                case ChatMessageConstants.SEND_TEXT:
                    handler.sendToText(channel, chatMessage);
                    break;
                // 图片
                case ChatMessageConstants.SEND_PICTURE:
                    handler.sendToText(channel, chatMessage);
                    break;
                // 语音
                case ChatMessageConstants.SEND_YY:
                    break;
                // 关闭
                case ChatMessageConstants.CLOSE_OTHER:
                    break;
                // 服务器反馈
                case ChatMessageConstants.SYSTEM_BACK_CLIENT:
                    break;
                // todo  客户端反馈 接收成功 已读
                case ChatMessageConstants.CLIENT_BACK_SYSTEM:
                    break;
//                //针对个人，发送给自己
//                case ChatMessageTypeConstants.SENDME:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDME);
//                    handler.verify(channel, maps);
//                    handler.sendMeText(channel, maps);
//                    break;
//                //针对个人，发送给某人
//                case ChatMessageTypeConstants.SENDTO:
//                    log.info(LogConstant.DefaultWebSocketHandler_SENDTO);
//                    handler.verify(channel, maps);
//                    handler.sendToText(channel, maps);
//                    break;
//                //发送给群组
//                case ChatMessageTypeConstants.SENDGROUP:
//                    log.info(LogConstant.DEFAULTWEBSOCKETHANDLER_SENDGROUP);
//                    handler.verify(channel, maps);
//                    handler.sendGroupText(channel, maps);
//                    break;
//                //发送图片，发送给自己
//                case ChatMessageTypeConstants.SENDPHOTOTOME:
//                    log.info("图片到个人");
//                    handler.verify(channel, maps);
//                    handler.sendPhotoToMe(channel, maps);
//                    break;
                default:
                    break;
            }

        } else {
            throw new NoFindHandlerException(NotInChatConstant.NOT_HANDLER);
        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELACTIVE, ctx.channel().remoteAddress().toString(), LogConstant.CHANNEL_SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(LogConstant.EXCEPTIONCAUGHT, ctx.channel().remoteAddress().toString(), LogConstant.DISCONNECT,cause);
        ctx.close();
    }

    @Override
    protected HandlerBaseService getHandlerService() {
        return handlerBaseService;
    }


    /**
     * 对聊天信息 进行校验
     *
     * @param chatMessage
     */
    private boolean validateChatMessage(Channel channel, ChatMessage chatMessage) {
        if (StringUtils.isBlank(chatMessage.getSendUserCode())) {
            log.warn("{} send message's sendUserCode is empty or null", channel.remoteAddress().toString());
            return false;
        }

        if (StringUtils.isBlank(chatMessage.getRecvUserCode())) {
            log.warn("{} send message's recvUserCode is empty or null", channel.remoteAddress().toString());
        }

        return true;

    }


}
