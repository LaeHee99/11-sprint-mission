package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFMessageRepository implements MessageRepository {
    List<Message> messages = new ArrayList<>();

    @Override
    public void save(Message message) {
        messages.add(message);
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return messages.stream()
                .filter(message -> message.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Message> findAll() {
        return messages;
    }

    @Override
    public void delete(Message message) {
        messages.remove(message);
    }

    @Override
    public void update(Message message) {
        for (int i = 0; i < messages.size(); i++) {
            if(messages.get(i).getId().equals(message.getId())) {
                messages.set(i, message);
                break;
            }
        }
    }
}
