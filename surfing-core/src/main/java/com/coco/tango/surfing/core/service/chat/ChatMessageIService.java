package com.coco.tango.surfing.core.service.chat;

import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessageState;

import java.util.List;

/**
 * 聊天消息
 *
 * @author ckli01
 * @date 2019-06-26
 */
public interface ChatMessageIService {


    ChatMessage save(ChatMessage chatMessage);

    /**
     * 批量保存
     * @param chatMessageStates
     */
    void batchSave(List<ChatMessageState> chatMessageStates);

    /**
     * 更新 消息状态 为成功
     * @param chatMessage
     */
    void updateMessageStateSuccess(ChatMessage chatMessage);

    /**
     * 根据 用户 code 获取 离线消息
     * @param userCode
     * @return
     */
    List<ChatMessage> offLineMessage(String userCode,String code);


}
