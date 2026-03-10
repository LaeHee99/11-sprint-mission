package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.io.*;
import java.util.*;

public class FileUserRepository implements UserRepository {
    private final String USER_FILE = "users.ser";


    private List<User> loadUsers() {
        File file = new File(USER_FILE);

        if(!file.exists()) {
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("유저 로드실패", e);
        }
    }

    private void saveUsers(List<User> users) {
        File file = new File(USER_FILE);

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException("유저 저장실패", e);
        }
    }

    @Override
    public void save(User user) {
        List<User> users = loadUsers();
        users.add(user);
        saveUsers(users);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return loadUsers().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return loadUsers().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return loadUsers();
    }

    @Override
    public void delete(User user) {
        List<User> users = loadUsers();
        users.removeIf(u -> u.getId().equals(user.getId()));
        saveUsers(users);
    }

    public void update(User user) {
        List<User> users = loadUsers();

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                break;
            }
        }

        saveUsers(users);
    }
}
