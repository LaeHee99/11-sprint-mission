package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.MessageEditHistory;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface MessageService {
    Message sendDirectMessage(User sender, User receiver, String content);
    Message sendChannelMessage(User sender, Channel channel, String content);
    Message getMessageById(UUID id);
    List<Message> getAllMessages();
    List<Message> getMessagesBySender(User sender);
    List<Message> getMessagesInChannel(Channel channel);
    void updateMessage(UUID messageId, String newContent);
    void deleteMessage(UUID messageId);
    List<MessageEditHistory> getMessageEditHistory(UUID messageId);
    boolean isMessageDeleted(UUID messageId);
}
