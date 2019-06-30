package com.coco.tango.surfing.core.service.chat;

import com.coco.tango.surfing.core.UTBase;
import com.coco.tango.surfing.core.dal.domain.chat.MqTopic;
import com.coco.tango.surfing.core.service.chat.MqTopicIService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * topic tag 测试
 *
 * @author ckli01
 * @date 2019-06-17
 */
public class MqTopicIServiceTest extends UTBase {


    @Autowired
    private MqTopicIService mqTopicIService;


    @Test
    public void inserTest() {

        MqTopic mqTopic = new MqTopic();

        mqTopic.setTopic("lck_test");
        mqTopic.setLastHostIp("127.0.0.1");
        mqTopic.setDeleted(1);


        boolean a= mqTopicIService.save(mqTopic);
        Assert.assertTrue(a);
    }
}

    
    
  