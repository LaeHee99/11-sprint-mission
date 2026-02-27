package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createChannel(String name);
    Channel getChannelById(UUID id);
    List<Channel> getAllChannels();
    Channel updateChannel(UUID id, String name);
    void deleteChannel(UUID id);
    void joinChannel(UUID id, UUID participantId);
    void leaveChannel(UUID id, UUID participantId);
}
