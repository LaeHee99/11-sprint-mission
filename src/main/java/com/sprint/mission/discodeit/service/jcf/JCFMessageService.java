package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data = new HashMap<>();

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
    public void update(UUID id, String messageContent) {
        Message message = data.get(id);
        message.updateContent(messageContent);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }

    @Override
    public String toString() {
        return data.toString();
    }
}
