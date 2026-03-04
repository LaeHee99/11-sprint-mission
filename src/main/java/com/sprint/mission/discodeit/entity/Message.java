package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message {

    private final String id;
    private final Long createdAt;
    private Long updatedAt;
    private String content;
    private String authorId;
    private String channelId;


    public Message(String content, String authorId, String channelId) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.content = content;
        this.authorId = authorId;
        this.channelId = channelId;
    }


    public String getId() { return id; }
    public String getContent() { return content; }
    public String getAuthorId() { return authorId; }
    public String getChannelId() { return channelId; }
    public Long getCreatedAt() { return createdAt; }
    public Long getUpdatedAt() { return updatedAt; }


    public void update(String content) {
        this.content = content;
        this.updatedAt = System.currentTimeMillis();
    }
}