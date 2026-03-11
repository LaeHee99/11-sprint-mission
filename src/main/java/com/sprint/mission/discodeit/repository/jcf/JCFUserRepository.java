package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> data;

    public JCFUserRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public void save(User user) {
        this.data.put(user.getId(), user);
    }

    @Override
    public User findById(UUID id) {
        User user = this.data.get(id);
        if (user == null) throw new IllegalArgumentException("requested user not found. ❌");

        return user;
    }

    @Override
    public boolean existByUsername(String username) {
        return this.data.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    @Override
    public boolean existByEmail(String email) {
        return this.data.values().stream()
                .anyMatch(user -> user.getEmail().equals(email));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.data.values());
    }

    @Override
    public void delete(User user) {
        this.data.remove(user.getId());
    }
}
