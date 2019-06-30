package com.coco.tango.surfing.chat.service.ws.impl;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.cache.local.WsCacheMap;
import com.coco.tango.surfing.chat.cache.redis.UserTopicCache;
import com.coco.tango.surfing.chat.service.ws.WsChannelService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户 连接 信息通道处理
 *
 * @author ckli01
 * @date 2019-06-12
 */
@Service
public class WebSocketChannelService implements WsChannelService {


    @Autowired
    private UserTopicCache userTopicCache;

    @Override
    public void loginWsSuccess(Channel channel, String userCode) {
        //  维护用户 - TOPIC
        WsCacheMap.saveWs(userCode, channel);
        WsCacheMap.saveAd(channel.remoteAddress().toString(), userCode);
        // 保存用户信息到redis
        userTopicCache.userTopic(userCode);
    }

    @Override
    public boolean hasOther(String otherOne) {
        return WsCacheMap.hasUserCode(otherOne);
    }

    @Override
    public Channel getChannel(String otherOne) {
        return WsCacheMap.getByUserCode(otherOne);
    }

    @Override
    public void close(Channel channel) {
        String token = WsCacheMap.getByAddress(channel.remoteAddress().toString());
        WsCacheMap.deleteAd(channel.remoteAddress().toString());
        WsCacheMap.deleteWs(token);
        channel.close();
    }

    @Override
    public boolean sendFromServer(Channel channel, Map<String, String> map) {
        try {
            channel.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(map)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String otherTopic(String otherOne) {
        return userTopicCache.get(otherOne);
    }
}
