package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;

public interface MessageService {

    Message sendMessage(UUID channelId, UUID userId, String content);

    List<Message> getMessages(UUID channelId, UUID userId);

    Message updateMessage(UUID messageId, UUID userId, String newContent);

    void deleteMessage(UUID messageId, UUID userId);
    
}
