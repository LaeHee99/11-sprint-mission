package com.sprint.mission.discodeit.service;

import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;

public interface UserService {
    
    // 단순 CRUD
    User createUser(String username, String password); // 사실상 회원가입 (register)
    User readUser(UUID userId);
    User findByUsername(String username); // 유저 이름으로 찾기
    User updateUserName(UUID userId, String username);
    boolean deleteUser(UUID userId);

    // 추가 기능
    User login(String username, String password); // 로그인
    boolean isUsernameTaken(String username); // 현재 유저가 이미 등록되어있는지

}
