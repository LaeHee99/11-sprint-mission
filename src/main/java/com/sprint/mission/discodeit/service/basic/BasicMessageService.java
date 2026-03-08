package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private static final Logger log = LoggerFactory.getLogger(BasicMessageService.class);

    public BasicMessageService(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Message createMessage(String content, UUID senderId, UUID channelId) {
        if (content.isEmpty()) throw new IllegalArgumentException("content is required. ❌");

        User sender = this.userRepository.findById(senderId);
        Channel channel = this.channelRepository.findById(channelId);

        if (!sender.getChannels().stream().anyMatch(ch -> ch.getId().equals(channel.getId()))) {
            throw new IllegalArgumentException("sender cannot send message without channel participation. ❌");
        }

        Message message = new Message(content, sender, channel);
        this.messageRepository.save(message);

        sender.addMessage(message);
        this.userRepository.save(sender);

        channel.addMessage(message);
        this.channelRepository.save(channel);

        log.info("Message has been created successfully. ✅ [ID: {}]", message.getId());
        log.info("-> {channel: {}, sender: {}, content: {}}", channel.getName(), sender.getNickname(), message.getContent());
        return message;
    }

    @Override
    public Message getMessageById(UUID id) {
        return this.messageRepository.findById(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return this.messageRepository.findAll();
    }

    @Override
    public Message updateMessage(UUID id, String content) {
        Message message = this.getMessageById(id);

        message.updateContent(content);
        this.messageRepository.save(message);

        log.info("Message has been updated successfully. ✅ [ID: {}]", id);
        return message;
    }

    @Override
    public void deleteMessage(UUID id) {
        Message message = this.getMessageById(id);

        User sender = message.getSender();
        sender.getMessages().removeIf(m -> m.getId().equals(message.getId()));
        this.userRepository.save(sender);

        Channel channel = message.getChannel();
        channel.getMessages().removeIf(m -> m.getId().equals(message.getId()));
        this.channelRepository.save(channel);

        this.messageRepository.delete(message);

        log.info("Message has been deleted successfully. ✅ [ID: {}]", id);
    }
}
