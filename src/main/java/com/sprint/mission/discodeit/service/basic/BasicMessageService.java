package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;

    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void createMessage(String message) {
        if(message == null || message.isBlank()) {
            throw new IllegalArgumentException("메세지 내용은 비어있을 수 없습니다.");
        }
        Message mes = new Message(message);
        messageRepository.save(mes);
        System.out.println(message + ", 생성 시간 : " + mes.getCreatedAt());
    }

    @Override
    public Message readMessage(UUID id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 메세지가 존재하지 않습니다."));
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
        if(message == null || message.isBlank()) {
            throw new IllegalArgumentException("메세지 내용은 비어있을 수 없습니다.");
        }

        Message mes = messageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메세지가 존재하지 않습니다."));
        if(mes.getMessage().equals(message)) {
            System.out.println("변경사항이 없습니다.");
            return;
        }

        mes.update(message);
        messageRepository.update(mes);
    }
}
