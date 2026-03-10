package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Message extends BaseEntity {

    private String content; // 내용
    private UUID userId; // 작성자
    private UUID channelId; // 채널 아이디

    public Message(String content, UUID userId, UUID channelId) {
        super();
        this.content = content;
        this.userId = userId;
        this.channelId = channelId;
    }

    // Getter
    public String getContent() {
        return content;
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getChannelId() {
        return channelId;
    }

    // Update
    // 댓글 내용 수정
    public void updateContent(String content) {
        this.content = content;
        this.touch();
    }

}
