package com.sprint.mission.discodeit.service.jcf;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

public class JCFUserService implements UserService {

    private static JCFUserService jcfUserService = new JCFUserService();
    // 두 가지 방향이 있는데
    // - 1. == null로 하고 생성 시점에서 만들어주기 -> 지연 초기화 (사용될 때 메모리에 올리자) / 
    //      - 추가로 지금 방식은 멀티쓰레드에서 null을 동시에 통과할 수도 있기 때문에 위험
    // - 2. 지금처럼 바로 메모리에 올려놓기
    // -> 근데 UserService는 어차피 사용을 바로 하기 때문에 2번이 더 나은 선택같음

    private final Map<UUID, User> userRepository; // 검색 속도: O(1)
    private final Map<String, UUID> usernameIndex; // 유저 이름 중복 방지를 위한 인덱스 흉내내보기 / 검색 속도: O(1), 메모리 사용량 조금 증가

    private JCFUserService() {
        this.userRepository = new HashMap<>();
        this.usernameIndex = new HashMap<>();
    }

    public static JCFUserService getJcfUserService() {
        // if (jcfUserService == null) {
        //     jcfUserService = new JCFUserService();
        // }
        return jcfUserService;
    }

    // 있으면 좋겠다 - 혹시 모르니깐 만들어놓자!
    @Override
    public boolean isUsernameTaken(String username) {
        return usernameIndex.containsKey(username); // true: 중복 O / false: 중복 X
    }

    @Override
    public User createUser(String username, String password) {
        // username 중복 검사)
        if (usernameIndex.containsKey(username)) {
            return null; // 중복된 username이라는 의미의 null
        }
        User newUser = new User(username, password);
        userRepository.put(newUser.getId(), newUser);
        usernameIndex.put(newUser.getUsername(), newUser.getId()); 
        // 궁금했던 점
        // - username vs newUser.getUsername()의 성능 차이
        // - JIT가 바꿔준다고 한다
        // - 결국 참조라 속도는 같다고 한다.
        // - 안전성 및 username을 가공할수도 있기 때문에 변환이 완료되 객체를 기반으로 하는 것이 좋다고 함
        return newUser;
    }

    @Override
    public User readUser(UUID userId) {
        User user = userRepository.get(userId);
        return user;
    }

    @Override 
    public User findByUsername(String username) {
        UUID userId = usernameIndex.get(username);
        if (userId == null) 
            return null;
        return userRepository.get(userId);
    }

    @Override
    public User updateUserName(UUID userId, String username) {
        // username 중복 검사
        if (usernameIndex.containsKey(username)) {
            return null;
        }

        User user = userRepository.get(userId);
        if (user != null) {
            String oldUsername = user.getUsername();
            usernameIndex.remove(oldUsername);
            user.updateUsername(username);
            // userRepository.put(user.getId(), user); => 꺼내온 user 객체의 변경은 사실 맵 안의 실제 객체가 바뀐 것
            usernameIndex.put(user.getUsername(), user.getId());
        }
        return user;
    }

    @Override
    public boolean deleteUser(UUID userId) {
        User user = userRepository.get(userId);
        if (user != null) {
            usernameIndex.remove(user.getUsername());
            userRepository.remove(user.getId());
            return true;
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        UUID userId = usernameIndex.get(username);

        if (userId == null)
            return null;

        User user = userRepository.get(userId);
        if (user.getPassword().equals(password)) {
            return user;
        }
        
        return null; // 실패했다는 null;
    }
    
}
