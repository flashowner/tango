package com.coco.tango.surfing.chat.bootstrap.server.handler;

import com.coco.tango.surfing.chat.constant.LogConstant;
import com.coco.tango.surfing.chat.exception.NotFindLoginChannlException;
import com.coco.tango.surfing.chat.service.ws.HandlerBaseService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty 事件 WS 处理 基础层
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Slf4j
public abstract class AbstractWsHandler extends SimpleChannelInboundHandler<WebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            log.info("TextWebSocketFrame : {}", msg);
            textDoMessage(ctx, (TextWebSocketFrame) msg);
        } else if (msg instanceof BinaryWebSocketFrame) {
            log.info("WebSocketFrame : {}", msg);
            binaryDoMessage(ctx, (BinaryWebSocketFrame) msg);
        }
    }

    protected abstract void binaryDoMessage(ChannelHandlerContext ctx, BinaryWebSocketFrame msg);

    protected abstract void textDoMessage(ChannelHandlerContext ctx, TextWebSocketFrame msg);

    protected abstract HandlerBaseService getHandlerService();

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info(LogConstant.CHANNELINACTIVE, ctx.channel().remoteAddress().toString(), LogConstant.CLOSE_SUCCESS);
        try {
            getHandlerService().close(ctx.channel());
        } catch (NotFindLoginChannlException e) {
            log.error(LogConstant.NOTFINDLOGINCHANNLEXCEPTION, ctx.channel().localAddress().toString(),e);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 超时
//            webSocketHandlerApi.doTimeOut(ctx.channel(),(IdleStateEvent)evt);
        }
        super.userEventTriggered(ctx, evt);
    }


    /**
     * 异常消息
     *
     * @param ctx   通道上下文
     * @param cause 线程
     * @throws Exception 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("异常消息: {}", cause.getMessage(), cause);
    }


}
