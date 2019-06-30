package com.coco.tango.surfing.chat.mq.producer;

import com.coco.tango.surfing.Application;
import com.coco.tango.surfing.chat.bootstrap.init.DistributeTopicInitHandler;
import com.coco.tango.surfing.chat.constant.ChatMessageConstants;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * @author ckli01
 * @date 2019-06-20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest(classes = Application.class)
public class TangoChatMessageProducerTest {

    @Autowired
    private TangoChatMessageProducer producer;


    @Test
    public void sendTest1() {

        ChatMessage chatMessage=new ChatMessage();

        chatMessage.setValue("heihei");
        chatMessage.setId(System.currentTimeMillis()+"");
        chatMessage.setType(ChatMessageConstants.CONN_OTHER);
        chatMessage.setSendUserCode("1");
        chatMessage.setRecvUserCode("2");
        chatMessage.setTime(new Date());
        chatMessage.setGroupId("1");

        producer.send(DistributeTopicInitHandler.TANGO_SURFING_TOPIC,chatMessage);
    }
}

    
    
  