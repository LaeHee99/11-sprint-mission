package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data;

    public JCFChannelService() {
        this.data = new HashMap<>();
    }

    @Override
    public Channel createChannel(String name, int capacity, User owner) {
        Channel channel = new Channel(name, capacity, owner);
        data.put(channel.getId(), channel);
        owner.getOwnedChannels().add(channel);
        owner.getJoinedChannels().add(channel);
        return channel;
    }

    @Override
    public Channel getChannelById(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Channel> getAllChannels() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void updateChannel(UUID id, String name, int capacity) {
        Channel channel = data.get(id);
        if (channel != null) {
            channel.update(name, capacity);
        }
    }

    @Override
    public boolean joinChannel(UUID channelId, User user) {
        Channel channel = data.get(channelId);
        if (channel == null) {
            return false;
        }
        boolean success = channel.addParticipant(user);
        if (success) {
            user.getJoinedChannels().add(channel);
        }
        return success;
    }

    @Override
    public boolean leaveChannel(UUID channelId, User user) {
        Channel channel = data.get(channelId);
        if (channel == null) {
            return false;
        }
        boolean success = channel.removeParticipant(user);
        if (success) {
            user.getJoinedChannels().remove(channel);
        }
        return success;
    }

    @Override
    public boolean kickUser(UUID channelId, User owner, User targetUser) {
        Channel channel = data.get(channelId);
        if (channel == null || !channel.getOwner().equals(owner)) {
            return false;
        }
        boolean success = channel.removeParticipant(targetUser);
        if (success) {
            targetUser.getJoinedChannels().remove(channel);
        }
        return success;
    }

    @Override
    public List<User> getChannelParticipants(UUID channelId) {
        Channel channel = data.get(channelId);
        if (channel == null) {
            return null;
        }
        return channel.getParticipants();
    }

    @Override
    public void deleteChannel(UUID id) {
        Channel channel = data.get(id);
        if (channel != null) {
            for (User participant : channel.getParticipants()) {
                participant.getJoinedChannels().remove(channel);
            }
            channel.getOwner().getOwnedChannels().remove(channel);
            data.remove(id);
        }
    }
}
