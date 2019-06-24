package com.coco.tango.surfing.chat.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.coco.tango.surfing.chat.bean.ChatMessage;
import com.coco.tango.surfing.chat.service.ws.impl.TangoHandlerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 聊天消息 rocketMq
 * 接收
 *
 * @author ckli01
 * @date 2019-06-20
 */
@Component
@Slf4j
public class TangoChatMessageConsumer implements RocketMQListener<ChatMessage> {

    @Autowired
    private TangoHandlerServiceImpl tangoHandlerService;


    @Override
    public void onMessage(ChatMessage message) {
        // todo 消息回传处理
        log.info("------- TangoChatMessageConsumer received: {} \n", JSONObject.toJSONString(message));
//        log.info("------- TangoChatMessageConsumer received: {} \n", message);
        tangoHandlerService.distributeMessageDeal(message);

    }
}

    
    
  