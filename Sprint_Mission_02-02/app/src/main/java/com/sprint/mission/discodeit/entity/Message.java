package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity {
    
    private String content;
    private UUID userId; // 메세지를 작성한 사람
    private UUID channelId; // 어떤 채널에 들어있는지

    public Message(String content, UUID userId, UUID channelId) {
        super();
        this.content = content;
        this.userId = userId;
        this.channelId = channelId;
    }

    public Message(UUID id, Long createdAt, Long updatedAt, String content, UUID userId, UUID channelId) {
        super(id, createdAt, updatedAt);
        this.content = content;
        this.userId = userId;
        this.channelId = channelId;
    }

    public String getContent() {
        return content;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    // 메세지 수정
    public Message updateContent(String newContent) {
        return new Message(this.id, this.createdAt, System.currentTimeMillis(), newContent, this.userId, this.channelId);
    }

}
