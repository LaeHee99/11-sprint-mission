package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> channeldata;

    public JCFChannelService() {

        this.channeldata = new HashMap<>();
    }


    @Override
    public Channel create(Channel channel) {
        channeldata.put(channel.getId(),channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {

        return Optional.ofNullable(channeldata.get(id));
    }

    @Override
    public List<Channel> findAll() {

        return new ArrayList<>(channeldata.values());
    }

    @Override
    public Channel update(Channel channel) {

        channeldata.put(channel.getId(),channel);
        return channel;
    }

    @Override
    public boolean delete(UUID id) {

        return channeldata.remove(id) !=null;
    }
}
