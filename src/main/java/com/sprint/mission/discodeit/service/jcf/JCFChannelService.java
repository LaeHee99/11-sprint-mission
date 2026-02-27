package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;
    private JCFUserService userService;
    private JCFMessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(JCFChannelService.class);

    public JCFChannelService() {
        this.data = new HashMap<>();
    }

    public void setUserService(JCFUserService userService) {
        this.userService = userService;
    }

    public void setMessageService(JCFMessageService messageService) {
        this.messageService = messageService;
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
        Channel channel = this.getChannelById(id);

        channel.updateName(name);

        log.info("{} channel has been updated successfully. ✅ [ID: {}]", name, id);
        return channel;
    }

    @Override
    public void deleteChannel(UUID id) {
        Channel channel = this.getChannelById(id);

        channel.getParticipants()
                .forEach(user -> user.getChannels().remove(channel));

        new ArrayList<>(channel.getMessages())
                .forEach(message -> this.messageService.deleteMessage(message.getId()));

        this.data.remove(id);

        log.info("{} channel has been deleted successfully. ✅ [ID: {}]", channel.getName(), id);
    }

    @Override
    public void joinChannel(UUID id, UUID participantId) {
        Channel channel = this.getChannelById(id);
        User participant = this.userService.getUserById(participantId);

        channel.getParticipants().add(participant);
        participant.getChannels().add(channel);

        log.info("{} has joined {} channel successfully. ✅", participant.getNickname(), channel.getName());
    }

    @Override
    public void leaveChannel(UUID id, UUID participantId) {
        Channel channel = this.getChannelById(id);
        User participant = this.userService.getUserById(participantId);

        channel.getParticipants().remove(participant);
        participant.getChannels().remove(channel);

        log.info("{} has left {} channel successfully. ✅", participant.getNickname(), channel.getName());
    }
}
