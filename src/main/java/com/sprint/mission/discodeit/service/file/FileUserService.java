package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;

import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;

public class FileUserService implements UserService {
    private UserRepository userRepository;

    public FileUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(String name, String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }
        User user = new User(name, email, password);
        userRepository.save(user);
        System.out.println(name + "유저가 생성되었습니다. 유저 이메일: " + email + ", 생성 시간 : " + user.getCreatedAt());
    }

    @Override
    public User readUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("입력하신 이메일이 존재하지 않습니다."));
        return user;
    }

    @Override
    public List<User> allReadUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(UUID id, String password) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        if(!user.getPassword().equals(password)) {
            throw new InputMismatchException("비밀번호가 일치하지 않습니다.");
        }

        userRepository.delete(user);
    }

    @Override
    public void updateUser(UUID id, String name, String email, String password) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        user.update(name, email, password);
        userRepository.update(user);
    }
}
