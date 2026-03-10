package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private MessageRepository messageRepository;

    public JCFMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void createMessage(String message) {
        Message mes = new Message(message);
        messageRepository.save(mes);
        System.out.println(message + ", 생성 시간 : " + mes.getCreatedAt());
    }

    @Override
    public Message readMessage(UUID id) {
        Message message = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메세지가 존재하지 않습니다."));
        return message;
    }

    @Override
    public List<Message> readAllMessage() {
        return messageRepository.findAll();
    }

    @Override
    public void deleteMessage(UUID id) {
        Message mes = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메세지가 존재하지 않습니다."));

        messageRepository.delete(mes);
    }

    @Override
    public void updateMessage(UUID id, String message) {
        Message mes = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업데이트 할 메세지가 존재하지 않습니다."));

        mes.update(message);

        messageRepository.update(mes);
    }
}
