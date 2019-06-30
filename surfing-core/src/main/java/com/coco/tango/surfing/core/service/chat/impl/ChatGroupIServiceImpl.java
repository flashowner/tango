package com.coco.tango.surfing.core.service.chat.impl;

import com.coco.tango.surfing.core.dal.domain.chat.ChatGroup;
import com.coco.tango.surfing.core.dal.domain.chat.ChatMessage;
import com.coco.tango.surfing.core.dal.repository.chat.ChatGroupRepository;
import com.coco.tango.surfing.core.service.chat.ChatGroupIService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 聊天群组
 *
 * @author ckli01
 * @date 2019-06-26
 */
@Service
public class ChatGroupIServiceImpl implements ChatGroupIService {

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Override
    public ChatGroup save(ChatGroup chatGroup) {
        return chatGroupRepository.save(chatGroup);
    }


    @Override
    public void save(ChatMessage chatMessage) {
        List<ChatGroup> chatGroups = Lists.newArrayList();

        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setGroupId(chatMessage.getGroupId());
        chatGroup.setUserCode(chatMessage.getSendUserCode());

        chatGroups.add(chatGroup);

        if (chatMessage.getRecvUserCode().contains(";")) {
            String[] recvUsers = chatMessage.getRecvUserCode().split(";");

            for (String str : recvUsers) {
                chatGroup = new ChatGroup();
                chatGroup.setGroupId(chatMessage.getGroupId());
                chatGroup.setUserCode(str);
                chatGroups.add(chatGroup);
            }
        } else {
            chatGroup = new ChatGroup();
            chatGroup.setGroupId(chatMessage.getGroupId());
            chatGroup.setUserCode(chatMessage.getRecvUserCode());

            chatGroups.add(chatGroup);
        }
        chatGroupRepository.saveAll(chatGroups);
    }


    @Override
    public List<String> searchByGroupId(String groupId) {
        List<ChatGroup> chatGroups = chatGroupRepository.searchByGroupId(groupId);
        return chatGroups.stream().map(ChatGroup::getUserCode).collect(Collectors.toList());
    }


    @Override
    public List<String> searchByUserCode(String sendUserCode) {
        List<ChatGroup> chatGroups = chatGroupRepository.searchByUserCode(sendUserCode);
        return chatGroups.stream().map(ChatGroup::getUserCode).collect(Collectors.toList());
    }
}

    
    
  