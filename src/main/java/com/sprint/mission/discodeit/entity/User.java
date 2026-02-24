package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private String name;
    private String email;
    private List<Message> sentMessages;
    private List<Message> receivedMessages;
    private List<Channel> joinedChannels;
    private List<Channel> ownedChannels;

    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
        this.sentMessages = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
        this.joinedChannels = new ArrayList<>();
        this.ownedChannels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Message> getSentMessages() {
        return sentMessages;
    }

    public List<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public List<Channel> getJoinedChannels() {
        return joinedChannels;
    }

    public List<Channel> getOwnedChannels() {
        return ownedChannels;
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
        setUpdatedAt(System.currentTimeMillis());
    }
}
