package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public abstract class BaseEntity implements Serializable {

    private UUID id;
    private Long createdAt;
    private Long updatedAt;

    public BaseEntity(){
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

    //getter
    public UUID getId() {
        return id;
    }
    public Long getCreatedAt(){
        return createdAt;
    }
    public Long getUpdatedAt(){
        return updatedAt;
    }

    public void updateTime(){
        this.updatedAt = System.currentTimeMillis();
    };

}
