package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {

    private final Map<UUID, Message> messageData = new HashMap<>();

    @Override
    public void save(Message message) {
        messageData.put(message.getId(), message);
    }

    @Override
    public Message findById(UUID id) {
        return messageData.get(id);
    }

    @Override
    public List<Message> findAll() {
        if (messageData.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(messageData.values());
    }

    @Override
    public void deleteById(UUID id) {
        messageData.remove(id);
    }
}