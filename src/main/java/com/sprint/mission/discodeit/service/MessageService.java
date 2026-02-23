package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    //create, read, readAll, update, delete

    void create(Message message);
    Message read(UUID id);
    List<Message> readAll();
    void save(Message message);
    void delete(UUID id);

    void setUserService(UserService userService);
    void setChannelService(ChannelService channelService);

    void clearMessagesInChannel(UUID channelId);
    void clearMessagesByUser(UUID userId);
}
