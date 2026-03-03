package com.sprint.mission.discodeit.repository;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelMember;
import com.sprint.mission.discodeit.repository.base.Repository;

public interface ChannelMemberRepository extends Repository<ChannelMember, UUID> {

    List<ChannelMember> findAllByChannelId(UUID channelId);
    List<ChannelMember> findAllByUserId(UUID userId);
    
}
