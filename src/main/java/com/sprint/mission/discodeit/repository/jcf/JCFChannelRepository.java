package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {

    private final Map<UUID, Channel> channelData = new HashMap<>();

    @Override
    public void save(Channel channel) {
        channelData.put(channel.getId(), channel);
    }

    @Override
    public Channel findById(UUID id) {
        return channelData.get(id);
    }

    @Override
    public List<Channel> findAll() {
        if (channelData.isEmpty()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(channelData.values());
    }

    @Override
    public void deleteById(UUID id) {
        channelData.remove(id);
    }
}