package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message createMessage(String contents, UUID userId, UUID channelId);
    Message findMessage(UUID id);
    List<Message> findAllMessage();
    void updateMessage(Message oldMessage, Message newMessage);
    void deleteMessage(Message message);
}
