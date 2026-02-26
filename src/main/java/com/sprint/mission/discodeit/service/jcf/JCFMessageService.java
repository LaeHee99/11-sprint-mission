package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;
    private final UserService userService;
    private final ChannelService channelService;
    private static final Logger log = LoggerFactory.getLogger(JCFMessageService.class);

    public JCFMessageService(UserService userService, ChannelService channelService) {
        this.data = new HashMap<>();
        this.userService = userService;
        this.channelService = channelService;
    }

    @Override
    public Message createMessage(String content, UUID senderId, UUID channelId) {
        User sender = this.userService.getUserById(senderId);
        Channel channel = this.channelService.getChannelById(channelId);

        Message message = new Message(content, sender, channel);
        this.data.put(message.getId(), message);
        sender.addMessage(message);
        channel.addMessage(message);

        log.info("Message has been created successfully. ✅ [ID: {}]", message.getId());
        log.info("-> {channel: {}, sender: {}, content: {}}", channel.getName(), sender.getNickname(), message.getContent());
        return message;
    }

    @Override
    public Message getMessageById(UUID id) {
        Message message = this.data.get(id);
        if (message == null) throw new IllegalArgumentException("requested message not found. ❌");

        return message;
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public Message updateMessage(UUID id, String content) {
        Message message = this.getMessageById(id);

        message.updateContent(content);

        log.info("Message has been updated successfully. ✅ [ID: {}]", id);
        return message;
    }

    @Override
    public void deleteMessage(UUID id) {
        Message message = this.getMessageById(id);

        message.getSender().getMessages().remove(message);
        message.getChannel().getMessages().remove(message);

        log.info("Message has been deleted successfully. ✅ [ID: {}]", id);
    }
}
