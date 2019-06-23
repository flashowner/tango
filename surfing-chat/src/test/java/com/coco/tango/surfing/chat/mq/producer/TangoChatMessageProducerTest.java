package com.coco.tango.surfing.chat.mq.producer;

import com.coco.tango.surfing.TestApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author ckli01
 * @date 2019-06-20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
@SpringBootTest(classes = TestApplication.class)
public class TangoChatMessageProducerTest {

    @Autowired
    private TangoChatMessageProducer producer;


    @Test
    public void sendTest1() {
        producer.send();
    }
}

    
    
  