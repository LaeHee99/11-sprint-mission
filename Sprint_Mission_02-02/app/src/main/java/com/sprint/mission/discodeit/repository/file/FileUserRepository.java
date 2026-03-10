package com.sprint.mission.discodeit.repository.file;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.base.FileRepository;

public class FileUserRepository extends FileRepository<User> implements UserRepository {

    private static FileUserRepository instance = new FileUserRepository();

    private final Map<String, User> usernameIndex = new ConcurrentHashMap<>();

    private FileUserRepository() {
        super("user.dat");
        rebuildIndex();
    }

    public static FileUserRepository getInstance() {
        return instance;
    }

    private void rebuildIndex() {
        usernameIndex.clear();
        for (User user : findAll()) {
            usernameIndex.put(user.getUsername(), user);
        }
    }

    @Override
    public synchronized User save(User user) {

        Optional<User> existingUser = findById(user.getId());

        if (existingUser.isPresent()) {
            String oldName = existingUser.get().getUsername();
            usernameIndex.remove(oldName);
        }

        User savedUser = super.save(user);
        usernameIndex.put(savedUser.getUsername(), savedUser);
        return savedUser;
    }

    @Override
    public synchronized void deleteById(UUID id) {
        Optional<User> target = findById(id);
        if (target.isPresent()) {
            usernameIndex.remove(target.get().getUsername());
        }

        super.deleteById(id);
    }

    //
    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usernameIndex.get(username));
    }
    
}
