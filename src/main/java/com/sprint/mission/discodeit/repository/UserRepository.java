package com.sprint.mission.discodeit.repository;

import com.sprint.mission.discodeit.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    public void init();
    public void save(User user);
    public User load(UUID id);
    public List<User> loadAll();
    public void delete(User user);
}
