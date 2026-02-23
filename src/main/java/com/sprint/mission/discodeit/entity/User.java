package com.sprint.mission.discodeit.entity;



import java.util.UUID;

public class User {
    private UUID id;
    private Long createdAtL;
    private Long updatedAtL;
    private String user;

    public User(String user) {
        this.id = UUID.randomUUID();
        this.createdAtL = System.currentTimeMillis();
        this.updatedAtL = this.createdAtL;
        this.user = user;
    }

    public void setUpdate(String user){
        this.updatedAtL = System.currentTimeMillis();
        this.user = user;
    }

    public UUID getId(){return id;}
    public Long getCreatedAtL() {return createdAtL;}

    public Long getUpdatedAtL() {return updatedAtL;}
    public String getUser() {return user;}
    @Override
    public String toString() {
        return String.format("User{id=%s, username='%s'}", id, user);
    }
}
