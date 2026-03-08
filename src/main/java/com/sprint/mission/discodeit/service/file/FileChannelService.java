package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class FileChannelService extends FileIOService<Channel> implements ChannelService {
    private FileUserService userService;
    private FileMessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(FileChannelService.class);

    public FileChannelService() {
        super(Channel.class);
    }

    public void setUserService(FileUserService userService) {
        this.userService = userService;
    }

    public void setMessageService(FileMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public Channel createChannel(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("name is required. ❌");
        if (existChannelByName(name)) throw new IllegalArgumentException("name cannot be duplicated. ❌");

        Channel channel = new Channel(name);
        this.save(channel);

        log.info("{} channel has been created successfully. ✅ [ID: {}]", name, channel.getId());
        return channel;
    }

    @Override
    public Channel getChannelById(UUID id) {
        Channel channel = this.findById(id);
        if (channel == null) throw new IllegalArgumentException("requested channel not found. ❌");

        return channel;
    }

    @Override
    public boolean existChannelByName(String name) {
        return this.findAll().stream()
                .anyMatch(channel -> channel.getName().equals(name));
    }

    @Override
    public List<Channel> getAllChannels() {
        return this.findAll();
    }

    @Override
    public Channel updateChannel(UUID id, String name) {
        Channel channel = this.getChannelById(id);

        channel.updateName(name);
        this.save(channel);

        log.info("{} channel has been updated successfully. ✅ [ID: {}]", name, id);
        return channel;
    }

    @Override
    public void deleteChannel(UUID id) {
        Channel channel = this.getChannelById(id);

        channel.getParticipants()
                .forEach(user -> {
                    user.getChannels().removeIf(ch -> ch.getId().equals(channel.getId()));
                    this.userService.save(user);
                });

        channel.getMessages()
                .forEach(message -> {
                    this.messageService.delete(message);
                });

        this.delete(channel);

        log.info("{} channel has been deleted successfully. ✅ [ID: {}]", channel.getName(), id);
    }

    @Override
    public void joinChannel(UUID id, UUID participantId) {
        Channel channel = this.getChannelById(id);
        User participant = this.userService.getUserById(participantId);

        if (channel.getParticipants().stream().anyMatch(p -> p.getId().equals(participant.getId()))) throw new IllegalArgumentException("duplicated participation is not allowed. ❌");

        channel.getParticipants().add(participant);
        this.save(channel);

        participant.getChannels().add(channel);
        this.userService.save(participant);

        log.info("{} has joined {} channel successfully. ✅", participant.getNickname(), channel.getName());
    }

    @Override
    public void leaveChannel(UUID id, UUID participantId) {
        Channel channel = this.getChannelById(id);
        User participant = this.userService.getUserById(participantId);

        channel.getParticipants().removeIf(p -> p.getId().equals(participant.getId()));
        this.save(channel);

        participant.getChannels().removeIf(ch -> ch.getId().equals(channel.getId()));
        this.userService.save(participant);

        log.info("{} has left {} channel successfully. ✅", participant.getNickname(), channel.getName());
    }
}
