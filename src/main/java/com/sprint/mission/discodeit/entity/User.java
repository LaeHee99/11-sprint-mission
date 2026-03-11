package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User {
    private final String id;
    private final Long createdAt;
    private Long updatedAt;
    private String name;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.name = name;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public Long getCreatedAt() { return createdAt; }
    public Long getUpdatedAt() { return updatedAt; }


    public void update(String name) {
        this.name = name;
        this.updatedAt = System.currentTimeMillis();
    }
}