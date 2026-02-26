package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.io.*;
import java.util.*;

public class FileMessageService implements MessageService {
    private final MessageRepository messageRepository;

    public FileMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public UUID create(Message message) {
        return messageRepository.create(message);
    }

    @Override
    public Message read(UUID id) {
        return messageRepository.read(id);
    }   // key인 id로 메세지 내용 읽기
    // 여기서 메세지는 보낸사람, 받는 사람 포함임

    @Override
    public List<Message> readAll(){
        return messageRepository.readAll();
    }   // value값들 list

    @Override
    public void update(UUID id, String messageContent) {
        Message message = messageRepository.read(id);
        message.updateContent(messageContent);
        messageRepository.create(message);
    }

    @Override
    public void delete(UUID id) {
        messageRepository.delete(id);
    }
}
