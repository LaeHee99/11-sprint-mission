package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;

public interface ChannelService {

    Channel createChannel(String channelName, UUID userId);
    void joinChannel(UUID channelId, UUID userId);
    void leaveChannel(UUID channelId, UUID userId);
    Channel updateChannelName(UUID channelId, UUID userId, String newChannelname);
    void deleteChannel(UUID channelId, UUID userId);
    Channel getChannel(UUID channelId);
    List<Channel> getAllChannels();
}
