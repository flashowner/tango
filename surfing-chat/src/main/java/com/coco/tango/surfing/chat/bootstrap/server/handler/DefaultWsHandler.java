package com.coco.tango.surfing.chat.bootstrap.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.constant.LogConstant;
import com.coco.tango.surfing.chat.service.ws.HandlerBaseService;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
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
public class DefaultWsHandler extends AbstractWsHandler {

    private HandlerBaseService handlerBaseService;

    public DefaultWsHandler(HandlerBaseService handlerBaseService) {
        this.handlerBaseService = handlerBaseService;
    }

    @Override
    protected void binaryDoMessage(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) {
    }

    @Override
    protected void textDoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        Channel channel = ctx.channel();
        log.info("textDoMessage : {}",msg.text());
        ChatMessage chatMessage = JSONObject.parseObject(msg.text(), ChatMessage.class);
        if (!validateChatMessage(channel, chatMessage)) {
            return;
        }
        chatMessage.setTime(new Date());
        // 不同类型消息 不同 处理
        switch (chatMessage.getType()) {
            case ChatMessageConstants.LOGIN:
                handlerBaseService.login(channel, chatMessage);
                break;
            // 建立交流
            case ChatMessageConstants.CONN_OTHER:
                handlerBaseService.connOther(channel, chatMessage);
                break;
            // 文字交流
            case ChatMessageConstants.SEND_TEXT:
                handlerBaseService.sendToText(channel, chatMessage);
                break;
            case ChatMessageConstants.CLIENT_BACK_SYSTEM:
                //  客户端反馈 接收成功
                handlerBaseService.changeMessageState(chatMessage);
                break;
            default:
                break;
        }

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELACTIVE, ctx.channel().remoteAddress().toString(), LogConstant.CHANNEL_SUCCESS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(LogConstant.EXCEPTIONCAUGHT, ctx.channel().remoteAddress().toString(), LogConstant.DISCONNECT, cause);
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
