package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.List;

public interface ChannelService {
    void create(Channel channel);
    Channel findById(String id);
    List<Channel> findAll();
    void update(String id, String name);
    void delete(String id);
}