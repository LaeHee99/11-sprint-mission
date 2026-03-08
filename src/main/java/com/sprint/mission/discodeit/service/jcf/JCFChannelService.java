package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final JCFChannelRepository channelRepository;
    private final JCFUserRepository userRepository;
    private final JCFMessageRepository messageRepository;
    private static final Logger log = LoggerFactory.getLogger(JCFChannelService.class);

    public JCFChannelService(JCFChannelRepository channelRepository, JCFUserRepository userRepository, JCFMessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public Channel createChannel(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("name is required. ❌");
        if (existChannelByName(name)) throw new IllegalArgumentException("name cannot be duplicated. ❌");

        Channel channel = new Channel(name);
        this.channelRepository.save(channel);

        log.info("{} channel has been created successfully. ✅ [ID: {}]", name, channel.getId());
        return channel;
    }

    @Override
    public Channel getChannelById(UUID id) {
        return this.channelRepository.findById(id);
    }

    @Override
    public boolean existChannelByName(String name) {
        return this.channelRepository.existByName(name);
    }

    @Override
    public List<Channel> getAllChannels() {
        return this.channelRepository.findAll();
    }

    @Override
    public Channel updateChannel(UUID id, String name) {
        Channel channel = this.getChannelById(id);

        channel.updateName(name);
        this.channelRepository.save(channel);

        log.info("{} channel has been updated successfully. ✅ [ID: {}]", name, id);
        return channel;
    }

    @Override
    public void deleteChannel(UUID id) {
        Channel channel = this.getChannelById(id);

        channel.getParticipants()
                .forEach(user -> {
                    user.getChannels().remove(channel);
                    this.userRepository.save(user);
                });

        new ArrayList<>(channel.getMessages())
                .forEach(message -> {
                    User sender = message.getSender();
                    sender.getMessages().remove(message);
                    this.userRepository.save(sender);
                    this.messageRepository.delete(message);
                });

        this.channelRepository.delete(channel);

        log.info("{} channel has been deleted successfully. ✅ [ID: {}]", channel.getName(), id);
    }

    @Override
    public void joinChannel(UUID id, UUID participantId) {
        Channel channel = this.getChannelById(id);
        User participant = this.userRepository.findById(participantId);

        if (channel.getParticipants().contains(participant)) throw new IllegalArgumentException("duplicated participation is not allowed. ❌");

        channel.getParticipants().add(participant);
        this.channelRepository.save(channel);

        participant.getChannels().add(channel);
        this.userRepository.save(participant);

        log.info("{} has joined {} channel successfully. ✅", participant.getNickname(), channel.getName());
    }

    @Override
    public void leaveChannel(UUID id, UUID participantId) {
        Channel channel = this.getChannelById(id);
        User participant = this.userRepository.findById(participantId);

        channel.getParticipants().remove(participant);
        this.channelRepository.save(channel);

        participant.getChannels().remove(channel);
        this.userRepository.save(participant);

        log.info("{} has left {} channel successfully. ✅", participant.getNickname(), channel.getName());
    }
}
