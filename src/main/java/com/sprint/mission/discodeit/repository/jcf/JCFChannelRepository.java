package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data;

    public JCFChannelRepository() {
        this.data = new HashMap<>();
    }


    @Override
    public void save(Channel channel) {
        this.data.put(channel.getId(), channel);
    }

    @Override
    public Channel findById(UUID id) {
        Channel channel = this.data.get(id);
        if (channel == null) throw new IllegalArgumentException("requested channel not found. ❌");

        return channel;
    }

    @Override
    public boolean existByName(String name) {
        return this.data.values().stream()
                .anyMatch(channel -> channel.getName().equals(name));
    }

    @Override
    public List<Channel> findAll() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public void delete(Channel channel) {
        this.data.remove(channel.getId());
    }
}
