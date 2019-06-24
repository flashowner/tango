package com.coco.tango.surfing.chat.bootstrap.server.handler;

import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.chat.constant.HttpConstant;
import com.coco.tango.surfing.chat.service.http.HttpChannelService;
import com.coco.tango.surfing.chat.util.HttpRequestDealUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * Http 请求处理
 *
 * @author ckli01
 * @date 2019-06-24
 */
@ChannelHandler.Sharable
@Slf4j
public class DefaultHttpHandler extends AbstractHttpHandler {

    private HttpChannelService httpChannelService;

    public DefaultHttpHandler(HttpChannelService httpChannelService) {
        this.httpChannelService = httpChannelService;
    }

    @Override
    protected void httpDoMessage(ChannelHandlerContext ctx, FullHttpRequest request) {
        // 根据不同url判断
        String method = HttpRequestDealUtil.checkType(request);

        switch (method) {
            case HttpConstant.UPLOARD_IMG_FILE:
                httpChannelService.uploadFile(ctx,request, ChatMessageConstants.SEND_PICTURE);
                break;
            case HttpConstant.UPLOARD_YY_FILE:
                httpChannelService.uploadFile(ctx,request, ChatMessageConstants.SEND_YY);
                break;
        }

    }
}

    
    
  