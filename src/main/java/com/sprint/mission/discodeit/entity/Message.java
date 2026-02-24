package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class Message extends BaseEntity {
    private User sender;
    private User receiver;
    private String content;
    private Channel channel;
    private List<MessageEditHistory> editHistories;
    private boolean isDeleted;

    public Message(User sender, User receiver, String content) {
        super();
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.channel = null;
        this.editHistories = new ArrayList<>();
        this.isDeleted = false;
    }

    public Message(User sender, Channel channel, String content) {
        super();
        this.sender = sender;
        this.receiver = null;
        this.content = content;
        this.channel = channel;
        this.editHistories = new ArrayList<>();
        this.isDeleted = false;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String newContent) {
        MessageEditHistory history = new MessageEditHistory(this.content);
        this.editHistories.add(history);
        this.content = newContent;
        setUpdatedAt(System.currentTimeMillis());
    }

    public Channel getChannel() {
        return channel;
    }

    public List<MessageEditHistory> getEditHistories() {
        return editHistories;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void delete() {
        this.isDeleted = true;
        setUpdatedAt(System.currentTimeMillis());
    }

    public boolean isDM() {
        return channel == null;
    }

    public void update(String content) {
        setContent(content);
    }
}
