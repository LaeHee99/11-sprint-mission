package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.MessageEditHistory;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.MessageService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> data;

    public JCFMessageService() {
        this.data = new HashMap<>();
    }

    @Override
    public Message sendDirectMessage(User sender, User receiver, String content) {
        Message message = new Message(sender, receiver, content);
        data.put(message.getId(), message);
        sender.getSentMessages().add(message);
        receiver.getReceivedMessages().add(message);
        return message;
    }

    @Override
    public Message sendChannelMessage(User sender, Channel channel, String content) {
        Message message = new Message(sender, channel, content);
        data.put(message.getId(), message);
        sender.getSentMessages().add(message);
        channel.addMessage(message);
        return message;
    }

    @Override
    public Message getMessageById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(data.values());
    }

    @Override
    public List<Message> getMessagesBySender(User sender) {
        return new ArrayList<>(sender.getSentMessages());
    }

    @Override
    public List<Message> getMessagesInChannel(Channel channel) {
        return new ArrayList<>(channel.getMessages());
    }

    @Override
    public void updateMessage(UUID messageId, String newContent) {
        Message message = data.get(messageId);
        if (message != null && !message.isDeleted()) {
            message.update(newContent);
        }
    }

    @Override
    public void deleteMessage(UUID messageId) {
        Message message = data.get(messageId);
        if (message != null) {
            message.delete();
        }
    }

    @Override
    public List<MessageEditHistory> getMessageEditHistory(UUID messageId) {
        Message message = data.get(messageId);
        if (message == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(message.getEditHistories());
    }

    @Override
    public boolean isMessageDeleted(UUID messageId) {
        Message message = data.get(messageId);
        return message != null && message.isDeleted();
    }
}
