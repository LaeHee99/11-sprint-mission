package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;
import com.sprint.mission.discodeit.entity.Message;

public interface MessageService {

    /**
     * 메시지 전송
     */
    Message sendMessage(String content, UUID userId, UUID channelId);

    /**
     * 메시지 수정
     * - 내용만 수정 (작성자 검증은 호출하는 쪽이나 내부에서 처리)
     */
    Message updateMessage(UUID messageId, String updateContent);

    /**
     * 채널의 메시지 목록 조회
     * - 해당 채널의 모든 메시지를 리스트로 반환
     */
    List<Message> getMessages(UUID channelId);

    /**
     * 메시지 삭제
     * - 작성자 본인인지 확인 후 삭제
     */
    boolean deleteMessage(UUID messageId, UUID userId, UUID channelId);

    /**
     * 채널 삭제 시 뒤처리
     * - 해당 채널의 모든 메시지를 삭제
     */
    void deleteAllByChannelId(UUID channelId);

    Message readMessage(UUID messageId);
}