package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> data;

    public JCFMessageRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public void save(Message message) {
        this.data.put(message.getId(), message);
    }

    @Override
    public Message findById(UUID id) {
        Message message = this.data.get(id);
        if (message == null) throw new IllegalArgumentException("requested message not found. ❌");

        return message;
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public void delete(Message message) {
        this.data.remove(message.getId());
    }
}
