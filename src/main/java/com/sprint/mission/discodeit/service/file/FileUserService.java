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
        public UUID create(User user) {
            return userRepository.create(user);
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
            user.updateUserName(userName, userNickname, userStatus);
            userRepository.create(user);
        }   // key값인 id를 입력하고 수정할 내용적기

        @Override
        public void delete(UUID id) {
            userRepository.delete(id);
        }   // 삭제(key 값인 id값이 필요함)

    }
