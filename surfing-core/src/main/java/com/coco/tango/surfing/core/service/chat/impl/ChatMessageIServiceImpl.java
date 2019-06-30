package com.coco.tango.surfing.core.service.chat.impl;

import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessageState;
import com.coco.tango.surfing.core.dal.repository.chat.ChatMessageRepository;
import com.coco.tango.surfing.core.dal.repository.chat.ChatMessageStateRepository;
import com.coco.tango.surfing.core.service.chat.ChatMessageIService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 聊天消息 处理
 *
 * @author ckli01
 * @date 2019-06-26
 */
@Service
@Slf4j
public class ChatMessageIServiceImpl implements ChatMessageIService {


    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatMessageStateRepository chatMessageStateRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public void batchSave(List<ChatMessageState> chatMessageStates) {
        chatMessageStateRepository.saveAll(chatMessageStates);
    }

    @Override
    public void updateMessageStateSuccess(ChatMessage chatMessage) {
        log.info("ChatMessageIServiceImpl updateMessageStateSuccess for messageId: {} userCode: {} value: {}",
                chatMessage.getId(), chatMessage.getSendUserCode(), chatMessage.getValue());
        if (StringUtils.isEmpty(chatMessage.getValue())) {
            // 单个更新
            mongoTemplate.updateFirst(Query.query(Criteria
                            .where("userCode").is(chatMessage.getSendUserCode())
                            .and("chatMessageId").is(chatMessage.getId())),

                    Update.update("code", chatMessage.getCode()),
                    ChatMessageState.class
            );
        } else {
            // 批量更新
            String[] ids = chatMessage.getValue().toString().split(";");
            mongoTemplate.updateMulti(Query.query(Criteria
                            .where("userCode").is(chatMessage.getSendUserCode())
                            .and("chatMessageId").in(Sets.newHashSet(ids))),
                    Update.update("code", chatMessage.getCode()),
                    ChatMessageState.class
            );

        }
    }


    @Override
    public List<ChatMessage> offLineMessage(String userCode, String code) {
        List<ChatMessageState> chatMessageStates = chatMessageStateRepository.offLineMessage(userCode, code);

        if (CollectionUtils.isEmpty(chatMessageStates)) {
            return Lists.newArrayList();
        } else {
            List<String> chatMessageIds = chatMessageStates.stream().map(ChatMessageState::getChatMessageId).collect(Collectors.toList());
            return (List<ChatMessage>) chatMessageRepository.findAllById(chatMessageIds);
        }
    }
}

    
    
  