package com.sprint.mission.discodeit.service.jcf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

public class JCFChannelService implements ChannelService {

    private static JCFChannelService jcfChannelService = new JCFChannelService();

    private final Map<UUID, Channel> channelRepository;
    private final Map<String, UUID> channelNameIndex; // 채널명 인덱스

    private JCFChannelService() {
        this.channelRepository = new HashMap<>();
        this.channelNameIndex = new HashMap<>();
    }

    public static JCFChannelService getJcfChannelService() {
        return jcfChannelService;  
    }

    @Override
    public Channel createChannel(String channelName, UUID masterUserId) {
        if (channelNameIndex.containsKey(channelName)) {
            return null;
        }

        Channel newChannel = new Channel(channelName, masterUserId);
        channelRepository.put(newChannel.getId(), newChannel);
        channelNameIndex.put(newChannel.getName(), newChannel.getId());

        return newChannel;
    }

    @Override
    public Channel readChannel(UUID channelId) {
        Channel channel = channelRepository.get(channelId);
        return channel;
    }

    @Override
    public Channel findByChannelName(String channelName) {
        UUID channelId = channelNameIndex.get(channelName);
        if (channelId == null) 
            return null;
        return channelRepository.get(channelId);
    }

    @Override // 권한이 있어야함!! - 방장권한
    public Channel updateChannelName(UUID channelId, String channelName) {
        if (channelNameIndex.containsKey(channelName)) {
            return null;
        }

        Channel channel = channelRepository.get(channelId);
        if (channel != null) {
            String oldChannelName = channel.getName();
            channelNameIndex.remove(oldChannelName);
            channel.updateName(channelName);
            channelNameIndex.put(channel.getName(), channel.getId());
        }
        return channel;
    }

    @Override
    public boolean deleteChannel(UUID channelId) {
        Channel channel = channelRepository.get(channelId);
        if (channel != null) {
            channelNameIndex.remove(channel.getName());
            channelRepository.remove(channel.getId());
            return true;
        }
        return false;
    }

    // 모든 채널 리스트 가져오기 
    @Override
    public List<Channel> findAllChannelList() {
        return new ArrayList<>(channelRepository.values());
    }
    
}
