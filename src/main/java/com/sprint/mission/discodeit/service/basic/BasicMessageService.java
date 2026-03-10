package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {


    private final MessageRepository messageRepository;

    private UserService userService;
    private ChannelService channelService;


    public BasicMessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // setter
    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    public void create(Message message) {
        if (message.getSenderId() == null) {
            System.out.println("유저 id가 유효하지 않습니다.");
            return;
        }
        if (message.getChannelId() == null) {
            System.out.println("채널 id가 유효하지 않습니다.");
            return;
        }

        if (messageRepository.findById(message.getId()) == null) {
            messageRepository.save(message);
        } else {
            System.out.println("이미 생성된 메시지입니다");
        }
    }

    @Override
    public Message read(UUID id) {
        Message message = messageRepository.findById(id);
        if (message != null) {
            return message;
        } else {
            System.out.println("존재하지 않는 메시지입니다.");
            return null;
        }
    }

    @Override
    public List<Message> readAll() {
        List<Message> messages = messageRepository.findAll();
        if (messages.isEmpty()) {
            System.out.println("메시지가 존재하지 않습니다.");
        }
        return messages;
    }

    @Override
    public void save(Message message) {
        if (messageRepository.findById(message.getId()) != null) {
            messageRepository.save(message);
        } else {
            System.out.println("존재하지 않는 메시지입니다.");
        }
    }

    @Override
    public void delete(UUID id) {

        if (messageRepository.findById(id) != null) {
            messageRepository.deleteById(id);
        } else {
            System.out.println("삭제 할 수 없음(존재하지 않는 id)");
        }
    }
    @Override
    public void clearMessagesInChannel(UUID channelId) {
        List<Message> messages = readAll();
        if (messages != null) {
            messages.stream()
                    .filter(message -> message.getChannelId().equals(channelId))
                    .forEach(message -> this.delete(message.getId()));
        }
    }

    @Override
    public void clearMessagesByUser(UUID userId) {
        List<Message> messages = readAll();
        if (messages != null) {
            messages.stream()
                    .filter(message -> message.getSenderId().equals(userId))
                    .forEach(message -> this.delete(message.getId()));
        }
    }
}