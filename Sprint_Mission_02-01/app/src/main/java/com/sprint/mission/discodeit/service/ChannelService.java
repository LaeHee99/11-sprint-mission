package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;

public interface ChannelService {
    
    // CRUD
    Channel createChannel(String channelName, UUID masterUserId);
    Channel readChannel(UUID channelId);
    Channel findByChannelName(String channelName);
    Channel updateChannelName(UUID channelId, String channelName);
    boolean deleteChannel(UUID channelId);
    List<Channel> findAllChannelList();
}
