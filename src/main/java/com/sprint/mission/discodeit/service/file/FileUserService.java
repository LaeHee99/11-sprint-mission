package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class FileUserService extends FileUtil implements UserService {
    public FileUserService() {
        super("users");
    }

    @Override
    public void createUser(User user) {
        save(filePath(user.getId()), user);
    }

    @Override
    public User findUser(UUID id) {
        Path path = filePath(id);
        if(!Files.exists(path)) {
            throw new IllegalArgumentException("User Not Found");
        }
        return load(path, User.class);
    }

    @Override
    public List<User> findAllUser() {
        return loadAll(directory, User.class);
    }

    @Override
    public void updateUser(User oldUser, User newUser) {
        // UUID를 유지하기 위해 remove -> add 하지 않음
        oldUser.setName(newUser.getName());
        oldUser.setUserId(newUser.getUserId());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setEmail(newUser.getEmail());
        oldUser.update();
        save(filePath(oldUser.getId()), oldUser);
    }

    @Override
    public void deleteUser(User user) {
        delete(filePath(user.getId()));
    }
}
