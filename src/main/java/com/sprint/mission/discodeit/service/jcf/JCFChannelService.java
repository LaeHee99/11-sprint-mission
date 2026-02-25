package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Domain.BaseEntity;
import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> data = new HashMap<>();
    private final UserService userService;

    public JCFChannelService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addMember(UUID channelId, UUID userId) {
        if(userService.read(userId)==null){
            throw new IllegalArgumentException("존재하지 않는 유저입니다: " + userId);
        }
        Channel channel = data.get(channelId);
        if(channel==null){
            throw new IllegalArgumentException("존재하지 않는 채널입니다: " + channelId);
        }
        channel.addMemberId(userId);
    }

    @Override
    public void removeMember(UUID channelId, UUID userId) {
        Channel channel = data.get(channelId);
        if(channel==null){
            throw new IllegalArgumentException("존재하지 않는 채널입니다 : " + channelId);
        }
        channel.removeMemberId(userId);
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }
    
    @Override
    public UUID create(Channel channel) {
        data.put(channel.getId(), channel);
        return channel.getId();
    }

    @Override
    public Channel read(UUID id) { return data.get(id); }

    @Override
    public List<Channel> readAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public void update(UUID id, String newName, String newDescription) {
        Channel channel = data.get(id);
        channel.updateChannel(newName, newDescription);
    }




}
