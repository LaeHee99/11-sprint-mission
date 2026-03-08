package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final JCFMessageRepository messageRepository;
    private final JCFUserRepository userRepository;
    private final JCFChannelRepository channelRepository;
    private static final Logger log = LoggerFactory.getLogger(JCFMessageService.class);

    public JCFMessageService(JCFMessageRepository messageRepository, JCFUserRepository userRepository, JCFChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
    public Message createMessage(String content, UUID senderId, UUID channelId) {
        if (content == null || content.isBlank()) throw new IllegalArgumentException("content is required. ❌");

        User sender = this.userRepository.findById(senderId);
        Channel channel = this.channelRepository.findById(channelId);

        if (!sender.getChannels().contains(channel)) throw new IllegalArgumentException("sender cannot send message without channel participation. ❌");

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

        if (content != null && !content.isBlank()) message.updateContent(content);
        this.messageRepository.save(message);

        log.info("Message has been updated successfully. ✅ [ID: {}]", id);
        return message;
    }

    @Override
    public void deleteMessage(UUID id) {
        Message message = this.getMessageById(id);

        User sender =  message.getSender();
        sender.getMessages().remove(message);
        this.userRepository.save(sender);

        Channel channel = message.getChannel();
        channel.getMessages().remove(message);
        this.channelRepository.save(channel);

        this.messageRepository.delete(message);

        log.info("Message has been deleted successfully. ✅ [ID: {}]", id);
    }
}
