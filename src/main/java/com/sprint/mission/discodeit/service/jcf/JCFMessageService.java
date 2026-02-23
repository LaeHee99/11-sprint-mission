package com.sprint.mission.discodeit.service.jcf;


import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {

    private final Map<UUID, Message> messagedata;
    public JCFMessageService(){
        this.messagedata = new HashMap<>();
    }

    @Override
    public Message create(Message message) {
        messagedata.put(message.getId(), message);
        return message;
    }

    @Override
    public Optional<Message> findById(UUID id) {
        return Optional.ofNullable(messagedata.get(id));
    }

    @Override
    public List<Message> findAll() {
        return new ArrayList<>(messagedata.values());
    }

    @Override
    public Message update(Message message) {
        messagedata.put(message.getId(), message);
        return message;
    }

    @Override
    public boolean delete(UUID id) {
        return messagedata.remove(id) != null;
    }
}
