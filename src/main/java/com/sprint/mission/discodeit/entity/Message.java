package com.sprint.mission.discodeit.entity;

public class Message extends BaseEntity {
    private String content;
    private User sender;
    private Channel channel;

    public Message(String content, User sender, Channel channel) {
        this.content = content;
        this.sender = sender;
        this.channel = channel;
    }

    public String getContent() {
        return this.content;
    }

    public void updateContent(String content) {
        this.content = content;
        this.setUpdatedAt();
    }

    public User getSender() {
        return this.sender;
    }

    public Channel getChannel() {
        return this.channel;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content='" + this.content + '\'' +
                ", sender=" + this.sender.getNickname() +
                ", channel=" + this.channel.getName() +
                '}';
    }
}