package com.sprint.mission.discodeit.service.jcf;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelUser;
import com.sprint.mission.discodeit.service.ChannelUserService;

public class JCFChannelUserService implements ChannelUserService {
    
    private static JCFChannelUserService instance = new JCFChannelUserService();

    private final Map<UUID, ChannelUser> channelUserRepository;
    private final Map<UUID, List<ChannelUser>> channelIdIndex; // 채널 아이디 기반 -> 해당 채널 아이디에 해당하는 ChannelUser
    private final Map<UUID, List<ChannelUser>> userIdIndex; // 유저 아이디 기반 -> 해당 유저 아이디에 해당하는 ChannelUser

    private JCFChannelUserService() {
        this.channelUserRepository = new HashMap<>();
        this.channelIdIndex = new HashMap<>();
        this.userIdIndex = new HashMap<>();
    }

    public static JCFChannelUserService getJcfChannelUserService() {
        return instance;
    }

    // 채널 입장
    @Override
    public ChannelUser join(UUID channelId, UUID userId) {
        ChannelUser newChannelUser = new ChannelUser(channelId, userId);
        channelUserRepository.put(newChannelUser.getId(), newChannelUser);

        channelIdIndex
            .computeIfAbsent(channelId, k -> new ArrayList<>())
            .add(newChannelUser);

        userIdIndex
            .computeIfAbsent(userId, k -> new ArrayList<>())
            .add(newChannelUser);

        return newChannelUser;
    }

    // 채널 퇴장 - 유저랑 채널의 관계를 끊어버림
    @Override
    public void leave(UUID channelId, UUID userId) {
        List<ChannelUser> userLink = userIdIndex.get(userId);

        if (userLink == null) return;

        ChannelUser targetLink = null;
        for (ChannelUser cu : userLink) {
            if (cu.getChannelId().equals(channelId)) {
                targetLink = cu;
                break;
            }
        }

        if (targetLink != null) {
            channelUserRepository.remove(targetLink.getId());

            userLink.remove(targetLink);

            List<ChannelUser> channelLink = channelIdIndex.get(channelId);
            if (channelLink != null) {
                channelLink.remove(targetLink);
            }
            // 채널 삭제 완료
        } else {
            // 채널 삭제 안된
        }

    }

    // 채널 삭제 - 뒤처리
    @Override
    public void deleteAllByChannelId(UUID channelId) {
        List<ChannelUser> lists = channelIdIndex.get(channelId);

        if (lists != null) {
            for (ChannelUser cu : lists) {
                channelUserRepository.remove(cu.getId());
                
                List<ChannelUser> userByLists = userIdIndex.get(cu.getUserId());
                userByLists.remove(cu);
            }

            channelIdIndex.remove(channelId);
        }
    }

    // 참여중인 채널 조회
    @Override
    public List<ChannelUser> findAllByUserId(UUID userId) {
        List<ChannelUser> lists = userIdIndex.get(userId);
        if (lists == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(lists);
    }

    // 채널 내 유저들 조회
    @Override
    public List<ChannelUser> findAllByChannelId(UUID channelId) {
        List<ChannelUser> lists = channelIdIndex.get(channelId);
        if (lists == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(lists);
    }

}
