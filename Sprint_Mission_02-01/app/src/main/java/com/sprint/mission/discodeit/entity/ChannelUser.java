package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class ChannelUser extends BaseEntity {

    private UUID channelId;
    private UUID userId;

    public ChannelUser(UUID channelId, UUID userId) {
        super();
        this.channelId = channelId;
        this.userId = userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public UUID getUserId() {
        return userId;
    }
    
}
