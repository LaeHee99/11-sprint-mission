package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.entity.Domain.User;

import java.util.List;
import java.util.UUID;

public interface ChannelService {       // 인터페이스임. JCFChannelService가 받아서 구현
    // 생성, 단건 조회, 전체 조회, 수정, 삭제
    UUID create(Channel channel);

    // 단건 조회
    Channel read(UUID id);

    // 전체 조회
    List<Channel> readAll();

    // 수정
    void update(UUID id, String newName, String newDescription);

    // 삭제
    void delete(UUID id);
}
