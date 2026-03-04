package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;

public class JCFChannelService implements ChannelService {
    private final List<Channel> channels;

    public JCFChannelService() {
        this.channels = new ArrayList<>();
    }

    @Override
    public void create(Channel channel) { channels.add(channel); }

    @Override
    public Channel findById(String id) {
        return channels.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Channel> findAll() { return new ArrayList<>(channels); }

    @Override
    public void update(String id, String name) {
        Channel channel = findById(id);
        if (channel != null) channel.update(name);
    }

    @Override
    public void delete(String id) {
        channels.removeIf(c -> c.getId().equals(id));
    }
}