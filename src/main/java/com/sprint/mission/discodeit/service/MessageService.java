package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;

public interface MessageService {
    void create(Message message);
    Message findById(String id);
    List<Message> findAll();
    void update(String id, String content);
    void delete(String id);
}