package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createChannel(String name, int capacity, User owner);
    Channel getChannelById(UUID id);
    List<Channel> getAllChannels();
    void updateChannel(UUID id, String name, int capacity);
    boolean joinChannel(UUID channelId, User user);
    boolean leaveChannel(UUID channelId, User user);
    boolean kickUser(UUID channelId, User owner, User targetUser);
    List<User> getChannelParticipants(UUID channelId);
    void deleteChannel(UUID id);
}
