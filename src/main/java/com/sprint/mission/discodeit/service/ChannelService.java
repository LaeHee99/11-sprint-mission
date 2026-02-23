package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    Channel createChannel(ChannelType channelType, String name, String description);
    Channel findChannel(UUID id);
    List<Channel> findAllChannel();
    void updateChannel(Channel oldChannel, Channel newChannel);
    void deleteChannel(Channel channel);
}
