package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class JCFUserService implements UserService {

    private final List<User> users;

    public JCFUserService() {
        this.users = new ArrayList<>();
    }

    @Override
    public void create(User user) {
        users.add(user);
    }

    @Override
    public User findById(String id) {

        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public void update(String id, String name) {
        User user = findById(id);
        if (user != null) {
            user.update(name);
        }
    }

    @Override
    public void delete(String id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}