package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Channel extends BaseEntity {

    private String name; // 채널명
    private UUID masterUserId; // 채널장 아이디
    
    public Channel(String name, UUID masterUserId) {
        super();
        this.name = name;
        this.masterUserId = masterUserId;
    }

    // Getter
    public String getName() {
        return name;
    }

    public UUID getMasterUserId() {
        return masterUserId;
    }

    // Update
    // 채널명 수정
    public void updateName(String name) {
        this.name = name;
        this.touch();
    }
    
}
