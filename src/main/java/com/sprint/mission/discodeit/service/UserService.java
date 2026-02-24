package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(String nickname, String username, String email, String password, String phoneNumber);
    User getUserById(UUID id);
    List<User> getAllUsers();
    User updateUser(UUID id, String nickname, String username, String email, String password, String phoneNumber);
    void deleteUser(UUID id);
}