package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Domain.BaseEntity;
import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data = new HashMap<>();
    
    @Override
    public UUID create(Channel channel) {
        data.put(channel.getId(), channel);
        return channel.getId();
    }

    @Override
    public Channel read(UUID id) { return data.get(id); }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String newName, String newDescription) {
        Channel channel = data.get(id);
        channel.updateChannel(newName, newDescription);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
}
