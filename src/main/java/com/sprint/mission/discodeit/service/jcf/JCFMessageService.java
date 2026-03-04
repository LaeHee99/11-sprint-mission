package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;

public class JCFMessageService implements MessageService {
    private final List<Message> messages;

    public JCFMessageService() {
        this.messages = new ArrayList<>();
    }

    @Override
    public void create(Message message) { messages.add(message); }

    @Override
    public Message findById(String id) {
        return messages.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Message> findAll() { return new ArrayList<>(messages); }

    @Override
    public void update(String id, String content) {
        Message message = findById(id);
        if (message != null) message.update(content);
    }

    @Override
    public void delete(String id) {
        messages.removeIf(m -> m.getId().equals(id));
    }
}