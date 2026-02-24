package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(String name, String email);
    User getUserById(UUID id);
    List<User> getAllUsers();
    void updateUser(UUID id, String name, String email);
    void deleteUser(UUID id);
}
