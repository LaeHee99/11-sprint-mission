package com.sprint.mission.discodeit.entity;

import java.util.UUID;

public class User extends BaseEntity {
    
    private String username; // 사용자명 (고유해야함 - 로직으로 처리)
    private String password; // 비밀번호
    
    public User(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public User(UUID id, Long createdAt, Long updatedAt, String username, String password) {
        super(id, createdAt, updatedAt);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }

    // 사용자명 수정
    public User updateUsername(String newUsername) {
        return new User(this.id, this.createdAt, System.currentTimeMillis(), newUsername, this.password);
    }

    // 비밀번호 수정

}
