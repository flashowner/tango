package com.coco.tango.surfing.core.service.chat;

import com.coco.tango.surfing.core.UTBase;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ckli01
 * @date 2019-06-26
 */
public class ChatMessageIServiceTest extends UTBase {


    @Autowired
    private ChatMessageIService chatMessageIService;

    @Test
    public void saveTest() {

        ChatMessage chatMessage =new ChatMessage();


        chatMessage.setGroupId("1");
        chatMessage.setRecvUserCode("1");

        chatMessageIService.save(chatMessage);


        System.out.println(1);


    }
}

    
    
  