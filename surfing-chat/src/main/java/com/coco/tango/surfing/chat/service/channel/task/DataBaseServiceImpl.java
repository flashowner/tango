package com.coco.tango.surfing.chat.service.channel.task;


import com.coco.tango.surfing.chat.bean.ChatMessage;
import org.springframework.stereotype.Service;

/**
 * 写消息 基础服务
 *
 * @author ckli01
 * @date 2019-06-13
 */
@Service
public class DataBaseServiceImpl implements InChatToDataBaseService {
    //获取消息
    @Override
    public Boolean writeMessage(ChatMessage inChatMessage) {
        System.out.println(inChatMessage.toString());
        return true;
    }

}
