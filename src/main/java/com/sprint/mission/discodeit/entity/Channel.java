package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class Channel {
    private UUID id;
    private Long createdAtL;
    private Long updatedAtL;
    private String channel;

    public Channel(String channel) {
        this.id = UUID.randomUUID();
        this.createdAtL = System.currentTimeMillis();
        this.updatedAtL = this.createdAtL;
        this.channel = channel;
    }

    public void setUpdated(String channel){
        this.updatedAtL = System.currentTimeMillis();
        this.channel = channel;
    }

    public UUID getId(){return id;}
    public Long getCreatedAtL() {return createdAtL;}
    public Long getUpdatedAtL() {return updatedAtL;}
    public String getChannel() {return channel;}
}
