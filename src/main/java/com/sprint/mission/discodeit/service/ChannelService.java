package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    void createChannel(String name, String password);
    Channel readChannel(UUID id);
    List<Channel> allReadChannel();
    void deleteChannel(UUID id, String password);
    void updateChannel(UUID id, String name, String password);
}
