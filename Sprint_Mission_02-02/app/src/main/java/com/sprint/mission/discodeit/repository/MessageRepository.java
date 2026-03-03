package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.base.Repository;

public interface MessageRepository extends Repository<Message, UUID> {

    List<Message> findAllByChannelId(UUID channelId);
    List<Message> findAllByUserId(UUID userId);
    
}
