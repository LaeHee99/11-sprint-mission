package com.sprint.mission.discodeit.service.file;
import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.util.*;

public class FileChannelService implements ChannelService {
    private final ChannelRepository channelRepository;


    public FileChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public UUID create(Channel channel) {
        return channelRepository.create(channel);
    }

    @Override
    public Channel read(UUID id) { return channelRepository.read(id); }

    @Override
    public List<Channel> readAll() {
        return channelRepository.readAll();
    }

    @Override
    public void update(UUID id, String newName, String newDescription) {
        Channel channel = channelRepository.read(id);
        channel.updateChannel(newName, newDescription);
        channelRepository.create(channel);
    }

    @Override
    public void delete(UUID id) {
        channelRepository.delete(id);
    }
}
