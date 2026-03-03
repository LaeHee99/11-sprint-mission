package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelMember;
import com.sprint.mission.discodeit.repository.ChannelMemberRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelMemberRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.service.ChannelMemberService;

public class FileChannelMemberService implements ChannelMemberService {
    
    private static FileChannelMemberService instance = null;

    private final ChannelMemberRepository channelMemberRepository;

    private FileChannelMemberService(ChannelMemberRepository channelMemberRepository) {
        this.channelMemberRepository = channelMemberRepository;
    }

    public static FileChannelMemberService getInstance(ChannelMemberRepository channelMemberRepository) {
        if (instance == null) {
            instance = new FileChannelMemberService(channelMemberRepository);
        }
        return instance;
    }

    // 채널 - 유저 연관관계 생성
    @Override
    public ChannelMember addMember(UUID channelId, UUID userId, String role) {
        if (isMember(channelId, userId)) {
            throw new RuntimeException("이미 가입된 채널입니다.");
        }

        ChannelMember newChannelMember = new ChannelMember(channelId, userId, role);
        newChannelMember = channelMemberRepository.save(newChannelMember);
        return newChannelMember; 
    }

    // 해당 방에 가입되어있는 멤버가 맞는지 확인
    @Override
    public boolean isMember(UUID channelId, UUID userId) {
        return channelMemberRepository.findAllByChannelId(channelId).stream()
            .anyMatch(k -> k.getUserId().equals(userId));
    }

    // 해당 채널 떠나기 - 유저 - 채널 연관관계 삭제
    @Override
    public void leaveChannel(UUID channelId, UUID userId) {
        ChannelMember cm = channelMemberRepository.findAllByChannelId(channelId).stream()
            .filter(k -> k.getUserId().equals(userId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("가입되어있지 않은 채널입니다."));

        channelMemberRepository.deleteById(cm.getId());
    }

    @Override
    public void deleteAllByChannelId(UUID channelId) {
        List<ChannelMember> list = getMembers(channelId);
        for (ChannelMember cm : list) {
            channelMemberRepository.deleteById(cm.getId());
        }
    }

    // 특정 채널에 가입되어있는 유저 목록
    @Override
    public List<ChannelMember> getMembers(UUID channelId) {
        return channelMemberRepository.findAllByChannelId(channelId);
    }

    // 특정 유저가 가입되어있는 방 목록
    @Override
    public List<ChannelMember> getChannels(UUID userId) {
        return channelMemberRepository.findAllByUserId(userId);
    }

    @Override
    public boolean isMaster(UUID channelId, UUID userId) {
        return channelMemberRepository.findAllByChannelId(channelId).stream()
            .anyMatch(k -> k.getUserId().equals(userId) && k.getRole().equals("OWNER"));
    }

}
