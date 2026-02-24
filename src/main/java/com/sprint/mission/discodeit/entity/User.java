package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.List;

public class User extends BaseEntity {
    private String nickname;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private List<Channel> channels;
    private List<Message> messages;

    public User(String nickname, String username, String email, String password, String phoneNumber) {
        this.nickname = nickname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.channels = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getNickname() {
        return this.nickname;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
        this.setUpdatedAt();
    }

    public String getUsername() {
        return this.username;
    }

    public void updateUsername(String username) {
        this.username = username;
        this.setUpdatedAt();
    }

    public String getEmail() {
        return this.email;
    }

    public void updateEmail(String email) {
        this.email = email;
        this.setUpdatedAt();
    }

    public String getPassword() {
        return this.password;
    }

    public void updatePassword(String password) {
        this.password = password;
        this.setUpdatedAt();
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.setUpdatedAt();
    }

    public List<Channel> getChannels() {
        return this.channels;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    @Override
    public String toString() {
        return "User{" +
                "nickname='" + this.nickname + '\'' +
                ", username='" + this.username + '\'' +
                ", email='" + this.email + '\'' +
                ", phoneNumber='" + this.phoneNumber + '\'' +
                '}';
    }
}
