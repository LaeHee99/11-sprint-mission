package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class Channel extends BaseEntity {
    private String name;
    private int capacity;
    private User owner;
    private List<User> participants;
    private List<Message> messages;

    public Channel(String name, int capacity, User owner) {
        super();
        this.name = name;
        this.capacity = capacity;
        this.owner = owner;
        this.participants = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.participants.add(owner);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public User getOwner() {
        return owner;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public boolean addParticipant(User user) {
        if (participants.size() >= capacity) {
            return false;
        }
        if (!participants.contains(user)) {
            participants.add(user);
            return true;
        }
        return false;
    }

    public boolean removeParticipant(User user) {
        return participants.remove(user);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void update(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        setUpdatedAt(System.currentTimeMillis());
    }
}
