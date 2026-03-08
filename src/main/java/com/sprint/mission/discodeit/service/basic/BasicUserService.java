package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasicUserService implements UserService {

    //의존성 주입
    private final UserRepository userRepository;

    // 얘네는 setter로 주입
    private MessageService messageService;
    private ChannelService channelService;
    // 생성자로 주입
    public BasicUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // setter
    @Override
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Override
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void create(User user) {

        if (userRepository.findById(user.getId()) == null) {
            userRepository.save(user); // 저장 위임
        } else {
            System.out.println("이미 생성된 유저입니다");
        }
    }

    @Override
    public User read(UUID userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            return user;
        } else {
            System.out.println("존재하지 않는 유저입니다.");
            return null;
        }
    }

    @Override
    public List<User> readAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            System.out.println("유저가 존재하지 않습니다.");
        }
        return users;
    }

    @Override
    public void save(User user) {
        if (userRepository.findById(user.getId()) != null) {
            userRepository.save(user); // 저장 위임
        } else {
            System.out.println("존재하지 않는 유저입니다.");
        }
    }

    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId);
        if (user != null) {
            // 유저가 작성한 메시지도 삭제
            if (messageService != null) {
                messageService.clearMessagesByUser(userId);
            }

            // 유저가 속한 채널에서 유저 제외
            List<UUID> joinedChannels = new ArrayList<>(user.getJoinedChannelId());
            joinedChannels.forEach(channelId -> {
                if (channelId != null) {
                    Channel channel = channelService.read(channelId);
                    if (channel != null) {
                        channel.removeMember(userId);
                        channelService.save(channel);
                    }
                }
            });

            // 유저가 관리자인 채널을 삭제
            if (channelService != null) {
                channelService.deleteChannelByAdmin(userId);
            }

            // 실제 파일 삭제는 Repository
            userRepository.deleteById(userId);
        } else {
            System.out.println("존재하지 않는 유저입니다.");
        }
    }
}