package com.sprint.mission.discodeit.service;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelUser;

public interface ChannelUserService {

    /**
     * 채널 입장
     * 유저와 채널의 연결 관계(ChannelUser)를 생성
     */
    ChannelUser join(UUID channelId, UUID userId);

    /**
     * 채널 퇴장
     * 유저와 채널의 연결 관계를 끊rl자발적 퇴장).
     */
    void leave(UUID channelId, UUID userId);

    /**
     * 채널 삭제 시 뒤처리
     * 해당 채널에 연관된 모든 멤버십 데이터를 삭제
     */
    void deleteAllByChannelId(UUID channelId);

    /**
     * 내가 참여 중인 채널 목록 조회
     */
    List<ChannelUser> findAllByUserId(UUID userId);

    /**
     * 특정 채널의 참여자 목록 조회
     */
    List<ChannelUser> findAllByChannelId(UUID channelId);
}