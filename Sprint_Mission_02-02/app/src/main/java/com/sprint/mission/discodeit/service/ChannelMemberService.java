package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelMember;

public interface ChannelMemberService {
    
    ChannelMember addMember(UUID channelId, UUID userId, String role);
    boolean isMember(UUID channelId, UUID userId);
    void leaveChannel(UUID channelId, UUID userId);
    List<ChannelMember> getMembers(UUID channelId);
    List<ChannelMember> getChannels(UUID userId);
    boolean isMaster(UUID channelId, UUID userId);
    void deleteAllByChannelId(UUID channelId);
}
