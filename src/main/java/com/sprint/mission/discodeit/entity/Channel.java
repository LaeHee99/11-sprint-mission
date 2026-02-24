package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class Channel extends BaseEntity {
    private String name;
    private List<User> participants;
    private List<Message> messages;

    public Channel(String name) {
        this.name = name;
        this.participants = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void updateName(String name) {
        this.name = name;
        this.setUpdatedAt();
    }

    public List<User> getParticipants() {
        return this.participants;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + this.name + '\'' +
                '}';
    }
}
