package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.ChannelMember;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ChannelMemberService;
import com.sprint.mission.discodeit.service.UserService;

public class FileUserService implements UserService {

    private static FileUserService instance = null;

    private final UserRepository userRepository;
    private final ChannelMemberService channelMemberService;

    private FileUserService(UserRepository userRepository, ChannelMemberService channelMemberService) {
        this.userRepository = userRepository;
        this.channelMemberService = channelMemberService;
    }

    public static FileUserService getInstance(UserRepository userRepository, ChannelMemberService channelMemberService) {
        if (instance == null) {
            instance = new FileUserService(userRepository, channelMemberService);
        }
        return instance;
    }

    // 로그인
    @Override
    public User login(String username, String password) {
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> {throw new RuntimeException("아이디 혹은 패스워드가 잘못되었습니다.");});

        if (password.equals(user.getPassword())) {
            return user;
        } else {
            throw new RuntimeException("아이디 혹은 패스워드가 잘못되었습니다.");
        }
    }

    // 회원가입
    @Override
    public void register(String username, String password) {
        // username 중복 검사
        userRepository.findByUsername(username).ifPresent(user -> {
            throw new RuntimeException("이미 가입된 아이디입니다.");
        });

        // 새 유저 생성
        User newUser = new User(username, password);
        userRepository.save(newUser); 
        System.out.println("회원가입 성공");
    }

    // 회원탈퇴
    @Override
    public void deleteUser(UUID userId) {
        List<ChannelMember> list = channelMemberService.getChannels(userId);
        for (ChannelMember cm : list) {
            channelMemberService.leaveChannel(cm.getChannelId(), userId);
        }

        userRepository.deleteById(userId);
    }
}
