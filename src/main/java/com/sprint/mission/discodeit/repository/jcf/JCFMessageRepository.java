package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;

import java.util.*;

public class JCFMessageRepository implements MessageRepository {
    private final Map<UUID, Message> data;

    public JCFMessageRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public UUID create(Message message) {
        data.put(message.getId(), message);
        return message.getId();
    }

    @Override
    public Message read(UUID id) {
        return data.get(id);
    }   // key인 id로 메세지 내용 읽기
    // 여기서 메세지는 보낸사람, 받는 사람 포함임

    @Override
    public List<Message> readAll(){
        return new ArrayList<>(data.values());
    }   // value값들 list

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
