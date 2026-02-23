package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class JavaApplication {
    public static void main(String[] args) {

        JCFUserService userService = new JCFUserService();
        JCFMessageService messageService = new JCFMessageService();
        JCFChannelService channelService = new JCFChannelService();

        userService.setMessageService(messageService);
        userService.setChannelService(channelService);
        channelService.setUserService(userService);
        channelService.setMessageService(messageService);
        messageService.setChannelService(channelService);
        messageService.setUserService(userService);

        //유저 만들기(유저 1,2)
        User user1 = new User("유저1","이메일1", "비번1");
        userService.create(user1);
        User user2 = new User("유저2","이메일2", "비번2");
        userService.create(user2);
        //유저 생성 확인

        //채널 만들기(유저1이 어드민)
        Channel channel1 = new Channel("채널1", user1.getId());
        channelService.create(channel1);
        Channel channel2 = new Channel("채널2", user2.getId());
        channelService.create(channel2);
        //채널 생성 확인

        /*System.out.println("\n===유저(ALL) 조회 테스트===");
        userService.readAll().stream()
                .forEach(System.out::println);

        System.out.println("\n===채널(ALL) 조회 테스트===");
        channelService.readAll().stream()
                .forEach(System.out::println);
        System.out.println("\n유저1 속한 채널");
        user1.getJoinedChannelId().stream()
                .map(channelId -> channelService.read(channelId).getChannelName())
                .forEach(System.out::println);

        channelService.addUserToChannel(user2.getId(), channel1.getId());
        System.out.println("\n채널1 멤버 목록");
        channel1.getMemberId().stream()
                .map(userId -> userService.read(userId).getUserName())
                .forEach(System.out::println);

        System.out.println("\n유저2 속한 채널");
        user2.getJoinedChannelId().stream()
                .map(channelId -> channelService.read(channelId).getChannelName())
                .forEach(System.out::println);*/


        //메시지 입력
        Message message1 = new Message("메시지1 내용", channel1.getId(), user1.getId());
        Message message2 = new Message("메시지2 내용", channel2.getId(), user1.getId());
        Message message3 = new Message("메시지3 내용", channel1.getId(), user1.getId());
        Message message4 = new Message("메시지4 내용", channel2.getId(), user2.getId());

        messageService.create(message1);
        messageService.create(message2);
        messageService.create(message3);
        messageService.create(message4);

        for(Message message : messageService.readAll()){
            System.out.println(message.getContent());
        }


        //메시지 수정
        message1.update("메시지1 내용 수정1", channel1.getId(), user1.getId());
        System.out.println(message1);
        //조회

        //유저1 삭제
        System.out.println("\n유저1 삭제");
        userService.delete(user1.getId());

        System.out.println("\n유저2 속한 채널");
        user2.getJoinedChannelId().stream()
                .map(channelId -> channelService.read(channelId).getChannelName())
                .forEach(System.out::println);

        for(Message message : messageService.readAll()){
            System.out.println(message.getContent());
        }
        //삭제 확인(유저, 채널, 메시지)

    }
}
