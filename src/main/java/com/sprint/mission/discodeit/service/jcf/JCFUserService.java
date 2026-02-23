package com.sprint.mission.discodeit.service.jcf;


import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> userdata;

    public JCFUserService(){
        this.userdata = new HashMap<>();
    }


    @Override
    public User create(User user) {
        userdata.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(userdata.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userdata.values());
    }

    @Override
    public User update(User user) {
        userdata.put(user.getId(), user);
        return user;
    }

    @Override
    public boolean delete(UUID id) {
        return userdata.remove(id) != null;
    }
}
