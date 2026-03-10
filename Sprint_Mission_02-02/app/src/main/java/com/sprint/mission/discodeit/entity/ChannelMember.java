package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class ChannelMember extends BaseEntity {

    private UUID channelId;
    private UUID userId;
    private String role; // OWNER / ADMIN / MEMBER 등

    public ChannelMember(UUID channUuid, UUID userId, String role) {
        super();
        this.channelId = channUuid;
        this.userId = userId;
        this.role = role;
    }

    public ChannelMember(UUID id, Long createdAt, Long updatedAt, UUID channUuid, UUID userId, String role) {
        super(id, createdAt, updatedAt);
        this.channelId = channUuid;
        this.userId = userId;
        this.role = role;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getRole() {
        return role;
    }

    // 
    public ChannelMember updateRole(String newRole) {
        return new ChannelMember(this.id, this.createdAt, System.currentTimeMillis(), this.channelId, this.userId, newRole);
    }
    
}
