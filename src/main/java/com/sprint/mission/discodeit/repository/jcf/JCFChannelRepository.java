package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class JCFChannelRepository implements ChannelRepository {
    List<Channel> channels = new ArrayList<>();

    @Override
    public void save(Channel channel) {
        channels.add(channel);
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        return channels.stream()
                .filter(channel -> channel.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Channel> findByPassword(String password) {
        return channels.stream()
                .filter(channel -> channel.getChannelPassword().equals(password))
                .findFirst();
    }

    @Override
    public List<Channel> findAll() {
        return channels;
    }

    @Override
    public void delete(Channel channel) {
        channels.remove(channel);
    }

    @Override
    public void update(Channel channel) {
        for (int i = 0; i < channels.size(); i++) {
            if(channels.get(i).getId().equals(channel.getId())) {
                channels.set(i, channel);
                return;
            }
        }
    }
}
