package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data ;

    public JCFChannelRepository() {
        this.data = new HashMap<>();
    }

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
    public void delete(UUID id) {
        data.remove(id);
    }

}
