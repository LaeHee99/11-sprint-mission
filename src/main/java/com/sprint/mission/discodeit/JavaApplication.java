package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.List;

public class JavaApplication {
    public static void main(String[] args) {

        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();

        System.out.println("=== 데이터 등록 ===");

        User user = new User("하빈");
        userService.create(user);


        Channel channel = new Channel("자유게시판");
        channelService.create(channel);


        Message message = new Message("안녕하세요!", user.getId(), channel.getId());
        messageService.create(message);

        System.out.println("등록 완료: " + user.getName() + ", " + channel.getName());

        System.out.println("\n=== 데이터 조회  ===");

        List<User> allUsers = userService.findAll();
        System.out.println("현재 유저 수: " + allUsers.size());


        Message foundMessage = messageService.findById(message.getId());
        System.out.println("찾은 메시지 내용: " + foundMessage.getContent());

        System.out.println("\n=== 데이터 수정 ===");

        userService.update(user.getId(), "김하빈");
        User updatedUser = userService.findById(user.getId());
        System.out.println("수정된 이름: " + updatedUser.getName());
        System.out.println("수정 시각(updatedAt): " + updatedUser.getUpdatedAt());

        System.out.println("\n=== 데이터 삭제 ===");
        // 채널 삭제
        channelService.delete(channel.getId());
        if (channelService.findById(channel.getId()) == null) {
            System.out.println("채널 삭제 확인 완료.");
        }
    }
}