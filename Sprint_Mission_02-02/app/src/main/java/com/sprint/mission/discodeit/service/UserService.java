package com.sprint.mission.discodeit.service;

import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;

public interface UserService {

    User login(String username, String password);
    void register(String username, String password);
    void deleteUser(UUID userId);

}
