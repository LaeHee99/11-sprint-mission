package com.sprint.mission.discodeit.repository;

import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.base.Repository;

public interface UserRepository extends Repository<User, UUID> {
    
    // 유저만의 특별한 기능
    Optional<User> findByUsername(String username);
}
