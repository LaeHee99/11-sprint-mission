package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.*;

public class JCFUserService implements UserService {
    private final Map<UUID, User> data = new HashMap<>();

    @Override
    public UUID create(User user) {
        data.put(user.getId(), user);
        return user.getId();
    }

    @Override
    public User read(UUID id) {
        return data.get(id);
    }       // 단건조회(key값인 id 넣기)

    @Override
    public List<User> readAll(){
        return new ArrayList<>(data.values());
    }   // 싹다 조회. 리스트로

    @Override
    public void update(UUID id, String userName, String userNickname, String userStatus) {
        User user = data.get(id);
        user.updateUserName(userName, userNickname, userStatus);
    }   // key값인 id를 입력하고 수정할 내용적기

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }   // 삭제(key 값인 id값이 필요함)

    @Override
    public String toString() {
        return data.toString();
    }   // 문자열 반환

}
