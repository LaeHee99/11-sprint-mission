package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel implements Serializable {
    private final UUID id;
    private static final long serialVersionUID = 1L;
    private final long createdAt;
    private long updatedAt;
    private String name;
    private String channelPassword;

    // 생성자 초기화
    public Channel(String name, String channelPassword) {
        this.id = UUID.randomUUID();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.name = name;
        this.channelPassword = channelPassword;
    }

    // 필드를 수정하는 update 함수
    public void update(String name, String password) {
        this.name = name;
        this.channelPassword = password;
        this.updatedAt = System.currentTimeMillis();
        System.out.println("채널의 정보가 수정되었습니다. 수정시간: " + getUpdatedAt());
    }

    // id 반환
    public UUID getId() {
        return id;
    }

    // 생성시간 반환
    public String getCreatedAt() {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.systemDefault());
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formattedDate;
    }

    // 업데이트 시간 반환
    public String getUpdatedAt() {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedAt), ZoneId.systemDefault());
        String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return formattedDate;
    }

    public String getName() {
        return name;
    }

    public String getChannelPassword() {
        return channelPassword;
    }

    @Override
    public String toString() {
        return "채널명 : " + name + ", 생성 시간 : " + getCreatedAt();
    }
}
