package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;
    private final Logger log;

    public JCFChannelService(Logger log) {
        this.data = new HashMap<>();
        this.log = log;
    }

    @Override
    public Channel createChannel(String name) {
        Channel channel = new Channel(name);
        this.data.put(channel.getId(), channel);

        log.info("{} channel has been created successfully. ✅ [ID: {}]", name, channel.getId());
        return channel;
    }

    @Override
    public Channel getChannelById(UUID id) {
        Channel channel = this.data.get(id);
        if (channel == null) throw new IllegalArgumentException("requested channel not found. ❌");

        return channel;
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public Channel updateChannel(UUID id, String name) {
        Channel channel = this.data.get(id);
        if (channel == null) throw new IllegalArgumentException("requested channel not found. ❌");

        channel.updateName(name);

        log.info("{} channel has been updated successfully. ✅ [ID: {}]", name, id);
        return channel;
    }

    @Override
    public void deleteChannel(UUID id) {
        Channel channel = this.data.remove(id);
        if (channel == null) throw new IllegalArgumentException("requested channel not found. ❌");

        channel.getParticipants()
                .forEach(user -> user.getChannels().remove(channel));

        log.info("{} channel has been deleted successfully. ✅ [ID: {}]", channel.getName(), id);
    }
}
