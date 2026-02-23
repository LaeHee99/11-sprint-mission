package com.sprint.mission.discodeit.entity;



import java.util.UUID;

public class Message {
    private UUID id;
    private Long createdAtL;
    private Long updatedAtL;
    private String message;

    public Message(String message) {
        this.id = UUID.randomUUID();
        this.createdAtL = System.currentTimeMillis();
        this.updatedAtL = this.createdAtL;
        this.message = message;
    }

    public void setUpdate(String message){
        this.updatedAtL = System.currentTimeMillis();
        this.message = message;
    }

    public UUID getId(){return id;}
    public Long getCreatedAtL() {return createdAtL;}
    public Long getUpdatedAtL() {return updatedAtL;}
    public String getMessage() {return message;}
}
