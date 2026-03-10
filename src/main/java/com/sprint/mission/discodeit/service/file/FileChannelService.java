package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

public class FileChannelService implements ChannelService {
    private ChannelRepository channelRepository;

    public FileChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public void createChannel(String name, String password) {
        if(channelRepository.findByPassword(password).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 패스워드입니다.");
        }
        Channel channel = new Channel(name, password);
        channelRepository.save(channel);
        System.out.println(channel.getName() + " 채널이 생성되었습니다. 채널 생성시간 : " + channel.getCreatedAt());
    }

    @Override
    public Channel readChannel(UUID id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 채널이 존재하지 않습니다."));
        return channel;
    }

    @Override
    public List<Channel> allReadChannel() {
        return channelRepository.findAll();
    }

    @Override
    public void deleteChannel(UUID id, String password) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));
        if(!channel.getChannelPassword().equals(password)) {
            throw  new InputMismatchException("패스워드가 일치하지 않습니다.");
        }

        channelRepository.delete(channel);
    }

    @Override
    public void updateChannel(UUID id, String name, String password) {
        Channel channel = channelRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("채널이 존재하지 않습니다."));
        channel.update(name, password);
        channelRepository.update(channel);
    }
}
