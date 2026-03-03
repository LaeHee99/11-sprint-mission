package com.sprint.mission.discodeit.repository.file;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.sprint.mission.discodeit.entity.ChannelMember;
import com.sprint.mission.discodeit.repository.ChannelMemberRepository;
import com.sprint.mission.discodeit.repository.base.FileRepository;

public class FileChannelMemberRepository extends FileRepository<ChannelMember> implements ChannelMemberRepository {

    private static FileChannelMemberRepository instance = new FileChannelMemberRepository();

    private final Map<UUID, List<ChannelMember>> channelIndex = new ConcurrentHashMap<>(); // 채널 아이디에 해당하는 유저 수
    private final Map<UUID, List<ChannelMember>> userIndex = new ConcurrentHashMap<>(); // 유저 아이디에 해당하는 채널 수

    private FileChannelMemberRepository() {
        super("channel-member.dat");
        rebuildIndex();
    }

    public static FileChannelMemberRepository getInstance() {
        return instance;
    }

    private void rebuildIndex() {
        channelIndex.clear();
        userIndex.clear();

        for (ChannelMember cm : findAll()) {
            addToIndex(cm);
        }
    }

    private void addToIndex(ChannelMember cm) {
        channelIndex.computeIfAbsent(cm.getChannelId(), k -> new ArrayList<>()).add(cm);
        userIndex.computeIfAbsent(cm.getUserId(), k -> new ArrayList<>()).add(cm);
    }

    private void removeFromIndex(ChannelMember cm) {
        List<ChannelMember> channelIndexList = channelIndex.get(cm.getChannelId());
        if (channelIndexList != null) {
            // channelIndexList.remove(cm); // equals를 재정의 해주어야함
            channelIndexList.removeIf(m -> m.getId().equals(cm.getId()));
        }

        List<ChannelMember> userIndexList = userIndex.get(cm.getUserId());
        if (userIndexList != null) {
            userIndexList.removeIf(m -> m.getId().equals(cm.getId()));
        }
    }

    @Override 
    public synchronized ChannelMember save(ChannelMember cm) {
        findById(cm.getId()).ifPresent(this::removeFromIndex); // 업데이트 시 사용
        ChannelMember savedChannelMember = super.save(cm);
        addToIndex(savedChannelMember);
        return savedChannelMember;
    }

    @Override
    public synchronized void deleteById(UUID id) {
        findById(id).ifPresent(this::removeFromIndex);
        super.deleteById(id);
    }

    //
    @Override
    public List<ChannelMember> findAllByChannelId(UUID channelId) {
        List<ChannelMember> list = channelIndex.getOrDefault(channelId, new ArrayList<>());
        return Collections.unmodifiableList(new ArrayList<>(list));
        // 
    }

    @Override
    public List<ChannelMember> findAllByUserId(UUID userId) {
        List<ChannelMember> list = userIndex.getOrDefault(userId, new ArrayList<>());
        return Collections.unmodifiableList(new ArrayList<>(list));
    }
    
}
