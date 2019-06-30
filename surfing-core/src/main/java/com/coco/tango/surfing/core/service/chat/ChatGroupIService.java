package com.coco.tango.surfing.core.service.chat;

import com.coco.tango.surfing.core.dal.domain.chat.ChatGroup;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;

import java.util.List;

/**
 * 聊天群组
 *
 * @author ckli01
 * @date 2019-06-26
 */
public interface ChatGroupIService {

    /**
     * 保存 聊天群组
     *
     * @param chatGroup
     * @return
     */
    ChatGroup save(ChatGroup chatGroup);

    /**
     * 根据 聊天 消息 保存群组
     *
     * @param chatMessage
     */
    void save(ChatMessage chatMessage);

    /**
     * 根据 群组 Id 查询 群组成员信息
     *
     * @param groupId
     * @return
     */
    List<String> searchByGroupId(String groupId);

    /**
     * 根据 用户 code 查询 群组信息
     * @param sendUserCode
     * @return
     */
    List<String> searchByUserCode(String sendUserCode);
}
