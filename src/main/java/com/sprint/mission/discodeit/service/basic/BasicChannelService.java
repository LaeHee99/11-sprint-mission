package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BasicChannelService implements ChannelService {


    private final ChannelRepository channelRepository;

    private MessageService messageService;
    private UserService userService;

    public BasicChannelService(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void create(Channel channel) {
        if (channel.getAdminId() == null) {
            System.out.println("어드민 id가 유효하지 않습니다.");
            return;
        }


        if (channelRepository.findById(channel.getId()) == null) {
            channelRepository.save(channel); // Repository에게 저장 위임

            User admin = userService.read(channel.getAdminId());
            if (admin != null) {
                admin.joinChannel(channel.getId());
                userService.save(admin);
            }
        } else {
            System.out.println("이미 생성된 채널입니다");
        }
    }

    @Override
    public Channel read(UUID channelId) {
        Channel channel = channelRepository.findById(channelId);
        if (channel != null) {
            return channel;
        } else {
            System.out.println("존재하지 않는 채널입니다.");
            return null;
        }
    }

    @Override
    public List<Channel> readAll() {
        List<Channel> channels = channelRepository.findAll();
        if (channels.isEmpty()) {
            System.out.println("채널이 존재하지 않습니다.");
        }
        return channels;
    }

    @Override
    public void save(Channel channel) {
        if (channelRepository.findById(channel.getId()) != null) {
            channelRepository.save(channel);
        } else {
            System.out.println("존재하지 않는 채널입니다.");
        }
    }

    @Override
    public void delete(UUID channelId) {
        if (channelId == null) return;

        Channel channel = channelRepository.findById(channelId);
        if (channel == null) {
            System.out.println("존재하지 않는 채널입니다.");
            return;
        }

        // 멤버 연결 해제
        List<UUID> disconnectMembers = new ArrayList<>(channel.getMemberId());
        disconnectMembers.forEach(userId -> {
            if (userId != null) {
                User user = userService.read(userId);
                if (user != null) {
                    user.leaveChannel(channelId);
                    userService.save(user);
                }
            }
        });

        // 2. 메시지 삭제
        if (messageService != null) {
            messageService.clearMessagesInChannel(channelId);
        }


        channelRepository.deleteById(channelId);
    }

    @Override
    public void deleteChannelByAdmin(UUID adminId) {
        List<UUID> channelsToDelete = channelRepository.findAll().stream()
                .filter(channel -> channel.getAdminId().equals(adminId))
                .map(Channel::getId)
                .toList();

        for (UUID channelId : channelsToDelete) {
            this.delete(channelId);
        }
    }

    @Override
    public void addUserToChannel(UUID userId, UUID channelId) {
        if (userId == null || channelId == null) return;

        Channel channel = channelRepository.findById(channelId);
        User user = userService.read(userId);

        if (channel != null && user != null) {
            channel.addMember(userId);
            channelRepository.save(channel); // 변경된 채널 정보 저장

            user.joinChannel(channelId);
            userService.save(user);
        } else {
            System.out.println("채널이나 유저가 존재하지 않습니다.");
        }
    }

    @Override
    public void removeUserFromChannel(UUID userId, UUID channelId) {
        if (userId == null || channelId == null) return;

        Channel channel = channelRepository.findById(channelId);
        User user = userService.read(userId);

        if (channel != null && user != null) {
            channel.removeMember(userId);
            channelRepository.save(channel); // 변경된 채널 정보 저장

            user.leaveChannel(channelId);
            userService.save(user);
        } else {
            System.out.println("채널이나 유저가 존재하지 않습니다.");
        }
    }
}