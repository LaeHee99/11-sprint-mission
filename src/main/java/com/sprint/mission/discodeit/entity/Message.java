package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends Common{
    private String contents;
    private UUID userId;
    private UUID channelId;

    public Message(String contents, UUID userId, UUID channelId) {
        super();
        this.contents = contents;
        this.userId = userId;
        this.channelId = channelId;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public void setChannelId(UUID channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "contents='" + contents + '\'' +
                ", userId=" + userId +
                ", channelId=" + channelId +
                '}';
    }
}
