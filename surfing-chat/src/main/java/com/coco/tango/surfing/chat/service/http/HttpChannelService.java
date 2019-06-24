package com.coco.tango.surfing.chat.service.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;


/**
 * http 连接 处理
 *
 * @author ckli01
 * @date 2019-06-24
 */
public interface HttpChannelService {


    /**
     * 上传 文件
     *
     * @param ctx
     * @param request
     */
    void uploadFile(ChannelHandlerContext ctx, FullHttpRequest request,String fileType);


    /**
     * 返回信息
     * @param ctx
     * @param responseString
     * @param contentType
     * @param status
     */
    default void sendResponse(ChannelHandlerContext ctx, String responseString,
                              String contentType, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(responseString, CharsetUtil.UTF_8));

        response.headers().set(CONTENT_TYPE, contentType);
        response.headers().add("Access-Control-Allow-Origin", "*");

        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 返回错误信息
     * @param ctx
     * @param status
     * @param msg
     */
    default void sendError(ChannelHandlerContext ctx, HttpResponseStatus status, String msg) {
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
        response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

        // Close the connection as soon as the error message is sent.
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

}
