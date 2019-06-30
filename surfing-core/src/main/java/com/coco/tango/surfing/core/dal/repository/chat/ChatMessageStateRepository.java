package com.coco.tango.surfing.core.dal.repository.chat;

import com.coco.tango.surfing.core.dal.domain.chat.ChatMessageState;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * 离线消息
 *
 * @author ckli01
 * @date 2019-06-27
 */
public interface ChatMessageStateRepository extends MongoRepository<ChatMessageState, String> {

    @Query(value = "{\"userCode\":{\"$eq\":?0}, \"code\":{\"$eq\":?1}}")
    List<ChatMessageState> offLineMessage(String userCode, String code);

    @Query(value = "{\"userCode\":{\"$eq\":?0}, \"chatMessageId\":{\"$eq\":?1}}")
    ChatMessageState findByUserCodeAndChatMessageId(String sendUserCode, String id);


    @Query(value = "{\"userCode\":{\"$eq\":?0}, \"chatMessageId\":{\"$in\":?1}}")
    List<ChatMessageState> findByUserCodeAndChatMessageIds(String sendUserCode, List<String> ids);

}
