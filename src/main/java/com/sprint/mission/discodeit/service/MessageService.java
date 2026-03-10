package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    void createMessage(String message);
    Message readMessage(UUID id);
    List<Message> readAllMessage();
    void deleteMessage(UUID id);
    void updateMessage(UUID id, String message);
}
