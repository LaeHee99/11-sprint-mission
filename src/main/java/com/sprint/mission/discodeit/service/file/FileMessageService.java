package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class FileMessageService extends FileIOService<Message> implements MessageService {
    private final FileUserService userService;
    private final FileChannelService channelService;
    private static final Logger log = LoggerFactory.getLogger(FileMessageService.class);

    public FileMessageService(FileUserService userService, FileChannelService channelService) {
        super(Message.class);
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message createMessage(String content, UUID senderId, UUID channelId) {
        if (content.isEmpty()) throw new IllegalArgumentException("content is required. ❌");

        User sender = this.userService.getUserById(senderId);
        Channel channel = this.channelService.getChannelById(channelId);

        if (!sender.getChannels().stream().anyMatch(ch -> ch.getId().equals(channel.getId()))) {
            throw new IllegalArgumentException("sender cannot send message without channel participation. ❌");
        }

        Message message = new Message(content, sender, channel);
        this.save(message);

        sender.addMessage(message);
        this.userService.save(sender);

        channel.addMessage(message);
        this.channelService.save(channel);

        log.info("Message has been created successfully. ✅ [ID: {}]", message.getId());
        log.info("-> {channel: {}, sender: {}, content: {}}", channel.getName(), sender.getNickname(), message.getContent());
        return message;
    }

    @Override
    public Message getMessageById(UUID id) {
        Message message = this.findById(id);
        if (message == null) throw new IllegalArgumentException("requested message not found. ❌");

        return message;
    }

    @Override
    public List<Message> getAllMessages() {
        return this.findAll();
    }

    @Override
    public Message updateMessage(UUID id, String content) {
        Message message = this.getMessageById(id);

        message.updateContent(content);
        this.save(message);

        log.info("Message has been updated successfully. ✅ [ID: {}]", id);
        return message;
    }

    @Override
    public void deleteMessage(UUID id) {
        Message message = this.getMessageById(id);

        User sender = message.getSender();
        sender.getMessages().remove(message);
        this.userService.save(sender);

        Channel channel = message.getChannel();
        channel.getMessages().remove(message);
        this.channelService.save(channel);

        this.delete(message);

        log.info("Message has been deleted successfully. ✅ [ID: {}]", id);
    }
}
