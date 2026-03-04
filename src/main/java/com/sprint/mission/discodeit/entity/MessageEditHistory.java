package com.sprint.mission.discodeit.entity;

public class MessageEditHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String previousContent;
    private long editedAt;

    public MessageEditHistory(String previousContent) {
        super();
        this.previousContent = previousContent;
        this.editedAt = System.currentTimeMillis();
    }

    public String getPreviousContent() {
        return previousContent;
    }

    public long getEditedAt() {
        return editedAt;
    }
}
