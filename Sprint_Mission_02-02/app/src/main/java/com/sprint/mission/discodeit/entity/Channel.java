package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Channel extends BaseEntity {
    
    private String name;

    public Channel(String name) {
        this.name = name;
    }

    public Channel(UUID id, Long createdAt, Long updatedAt, String name) {
        super(id, createdAt, updatedAt);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // 채널명 수정
    public Channel updateChannelname(String newName) {
        return new Channel(this.id, this.createdAt, System.currentTimeMillis(), newName);
    }

}
