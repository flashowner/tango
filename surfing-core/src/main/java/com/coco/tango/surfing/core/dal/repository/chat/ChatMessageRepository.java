package com.coco.tango.surfing.core.dal.repository.chat;

import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 聊天消息 持久化
 *
 * @author ckli01
 * @date 2019-06-26
 */
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
}
