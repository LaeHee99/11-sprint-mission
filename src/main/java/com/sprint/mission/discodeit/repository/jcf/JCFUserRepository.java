package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.util.*;

public class JCFUserRepository implements UserRepository {
    private final Map<UUID, User> data;


    public JCFUserRepository(){
        this.data = new HashMap<>();
    }

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
    public void delete(UUID id) {
        data.remove(id);
    }   // 삭제(key 값인 id값이 필요함)

    @Override
    public String toString() {
        return data.toString();
    }   // 문자열 반환
}
