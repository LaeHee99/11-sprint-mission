package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    //create, read, readAll, update, delete
    void create(Channel channel);
    Channel read(UUID id);
    List<Channel> readAll();
    void save(Channel channel);
    void delete(UUID id);

    void setMessageService(MessageService messageService);
    void setUserService(UserService userService);

    void deleteChannelByAdmin(UUID userId);

    void addUserToChannel(UUID userId, UUID channelId);
    void removeUserFromChannel(UUID userId, UUID channelId);
}
