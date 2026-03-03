package com.sprint.mission.discodeit.service.jcf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

public class JCFMessageService implements MessageService {
    
    private static JCFMessageService instance = new JCFMessageService();

    private final Map<UUID, Message> messageRepository;
    private final Map<UUID, List<Message>> channelMessageIndex; // 채널 아이디 기반

    private JCFMessageService() {
        this.messageRepository = new HashMap<>();
        this.channelMessageIndex = new HashMap<>();
    }

    public static JCFMessageService getInstance() {
        return instance;
    }

    // 메세지 보내기
    @Override
    public Message sendMessage(String content, UUID userId, UUID channelId) {
        Message newMessage = new Message(content, userId, channelId);
        messageRepository.put(newMessage.getId(), newMessage);
        
        // 채널메세지인덱스에도 추가해야함
        channelMessageIndex
            .computeIfAbsent(channelId, k -> new ArrayList<>())
            .add(newMessage);

        return newMessage;
    }

    // 메세지 업데이트 (modify로 작명하는게 낫나.. 귀찮다..)
    @Override
    public Message updateMessage(UUID messageId, String updateContent) {
        Message message = messageRepository.get(messageId);
        if (message != null) {
            message.updateContent(updateContent);
            messageRepository.put(message.getId(), message);
        }

        // 채널메세지인덱스 추가 안해도 되려나?
        // - 같은 message를 참조하고 있기 때문에 상관없음

        return message;
    }

    @Override 
    public Message readMessage(UUID messageId) {
        return messageRepository.get(messageId);
    }

    // 메세지 보여주기보다는 - 해당 채널에 대한 메세지들 리스트를 건네주기
    @Override
    public List<Message> getMessages(UUID channelId) {
        List<Message> messages = channelMessageIndex.get(channelId);

        if (messages == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(messages); // 복사본을 반환!
    }

    // 메세지 삭제
    @Override
    public boolean deleteMessage(UUID messageId, UUID userId, UUID channelId) {
        Message message = messageRepository.get(messageId);

        if (message == null || !message.getUserId().equals(userId)) {
            return false;
        }

        messageRepository.remove(messageId);
        List<Message> channelMessages = channelMessageIndex.get(channelId);
        if (channelMessages != null) {
            channelMessages.remove(message);
        }
        
        return true;
    }

    // 채널 제거 시 해당 채널에 대한 모든 채팅 삭제
    @Override
    public void deleteAllByChannelId(UUID channelId) {
        // 참조하고 있으면 안지워지니깐 다 지워야함
        List<Message> messages = channelMessageIndex.get(channelId);

        if (messages != null) {
            for (Message message : messages) {
                messageRepository.remove(message.getId());
            }

            channelMessageIndex.remove(channelId);
        }
    }

}
