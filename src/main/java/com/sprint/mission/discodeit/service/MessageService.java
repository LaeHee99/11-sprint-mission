package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.entity.Domain.User;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    UUID create(Message message);

    // 단건 조회
    Message read(UUID id);

    // 전체 조회
    List<Message> readAll();

    // 수정
    void update(UUID id, String messageContent);

    // 삭제
    void delete(UUID id);
}
