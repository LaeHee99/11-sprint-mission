package com.sprint.mission.discodeit.service.file;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelMemberService;
import com.sprint.mission.discodeit.service.ChannelService;

public class FileChannelService implements ChannelService {
    
    private static FileChannelService instance;

    private final ChannelRepository channelRepository;
    private final ChannelMemberService channelMemberService;

    private FileChannelService(ChannelRepository channelRepository, ChannelMemberService channelMemberService) {
        this.channelRepository = channelRepository;
        this.channelMemberService = channelMemberService;
    }

    public static FileChannelService getInstance(ChannelRepository channelRepository, ChannelMemberService channelMemberService) {
        if (instance == null) {
            instance = new FileChannelService(channelRepository, channelMemberService);
        }
        return instance;
    }

    // 채널 만들기
    @Override
    public Channel createChannel(String channelName, UUID userId) {
        // 채널 생성
        Channel newChannel = new Channel(channelName);
        newChannel = channelRepository.save(newChannel);

        // 채널 - 유저 연관관계 생성 (만든다는 행위는 방장이니깐 OWNER 입력)
        channelMemberService.addMember(newChannel.getId(), userId, "OWNER");
    
        return newChannel;
    }

    // 채널 가입 (일반 유저)
    @Override
    public void joinChannel(UUID channelId, UUID userId) {
        // 채널 유무 확인
        channelRepository.findById(channelId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 채널입니다."));

        // 유저 해당 채널 가입 연관관계 생성
        channelMemberService.addMember(channelId, userId, "NORMAL");
    }

    // 채널 탈퇴
    @Override
    public void leaveChannel(UUID channelId, UUID userId) {
        channelMemberService.leaveChannel(channelId, userId);
    }

    // 채널명 변경
    @Override
    public Channel updateChannelName(UUID channelId, UUID userId, String newChannelname) {
        // 권한 검사
        if (!channelMemberService.isMaster(channelId, userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        Channel oldChannel = channelRepository.findById(channelId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 채널입니다."));

        Channel newChannel = oldChannel.updateChannelname(newChannelname);
        channelRepository.save(newChannel);
        return newChannel;
    }

    // 채널 삭제
    @Override
    public void deleteChannel(UUID channelId, UUID userId) {
        if (!channelMemberService.isMaster(channelId, userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        // 연관관계 삭제
        channelMemberService.deleteAllByChannelId(channelId);

        // 채널 삭제
        channelRepository.deleteById(channelId);
    }

    @Override
    public Channel getChannel(UUID channelId) {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() -> new RuntimeException("해당 채널은 존재하지 않습니다."));
        return channel;
    }

    @Override
    public List<Channel> getAllChannels() {
        return Collections.unmodifiableList(channelRepository.findAll());
    }

}
