    package com.sprint.mission.discodeit.service.file;
    import com.sprint.mission.discodeit.entity.Domain.User;
    import com.sprint.mission.discodeit.repository.UserRepository;
    import com.sprint.mission.discodeit.service.UserService;

    import java.io.*;
    import java.util.*;

    public class FileUserService implements UserService {
        private final UserRepository userRepository;

        public FileUserService(UserRepository userRepository){
            this.userRepository = userRepository;
        }

        @Override
        public UUID create(User user) {         // 같은 이름을 가진 유저 확인 > 중복시 생성 안됨.
            // TODO 중복, null, blank 방지하기
            if(user == null){
                throw new IllegalArgumentException("유저가 null입니다.");
            }
            if(user.getUserName() ==null){
                throw new IllegalArgumentException("유저명이 null입니다.");
            }
            if(user.getUserName().isBlank()){
                throw new IllegalArgumentException("유저명이 blank입니다.");
            }
            boolean isDuplicate = userRepository.readAll().stream()
                    .anyMatch(u -> u.getUserName().equals(user.getUserName()));
                    // anymatch 하나라도 조건 만족시 trrue반환, true시 이미 존재하는 유저명
            if(isDuplicate){
                throw new IllegalArgumentException("이미 존재하는 유저입니다.");
            }
            return userRepository.create(user);     // 존재하는 유저가 아닐 시 새로운 유저 만들기.
        }

        @Override
        public User read(UUID id) {
            return userRepository.read(id);
        }       // 단건조회(key값인 id 넣기)

        @Override
        public List<User> readAll(){
            return userRepository.readAll();
        }   // 싹다 조회. 리스트로

        @Override
        public void update(UUID id, String userName, String userNickname, String userStatus) {
            User user = userRepository.read(id);
            // TODO 중복, null, blank 방지하기
            if(user==null){
                throw new IllegalArgumentException("존재하지 않는 유저입니다.");
            }
            if(userName == null){
                throw new IllegalArgumentException("유저명이 null입니다.");
            }
            if(userName.isBlank()){
                throw new IllegalArgumentException("유저명이 blank입니다.");
            }
            boolean isDuplicate = userRepository.readAll().stream()
                    .anyMatch(u -> u.getUserName().equals(userName));
            // anymatch 하나라도 조건 만족시 trrue반환, true시 이미 존재하는 유저명
            if(isDuplicate){
                throw new IllegalArgumentException("이미 존재하는 유저입니다.");
            }
            user.updateUserName(userName, userNickname, userStatus);
            userRepository.create(user);
        }   // key값인 id를 입력하고 수정할 내용적기

        @Override
        public void delete(UUID id) {
            User user = userRepository.read(id);
            if(user == null){
                throw new IllegalArgumentException("존재하지 않는 유저입니다.");
            }
            userRepository.delete(id);
        }   // 삭제(key 값인 id값이 필요함)

    }
