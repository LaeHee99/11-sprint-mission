package com.sprint.mission.discodeit.entity;

import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFChannelRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFMessageRepository;
import com.sprint.mission.discodeit.repository.jcf.JCFUserRepository;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;

public class JavaApplication {
    public static void main(String[] args) {

        UserRepository userRepository = new FileUserRepository();
        MessageRepository messageRepository = new FileMessageRepository();
        ChannelRepository channelRepository = new FileChannelRepository();

        BasicUserService userService = new BasicUserService(userRepository);
        BasicMessageService messageService = new BasicMessageService(messageRepository);
        BasicChannelService channelService = new BasicChannelService(channelRepository);

        userService.setMessageService(messageService);
        userService.setChannelService(channelService);
        channelService.setUserService(userService);
        channelService.setMessageService(messageService);
        messageService.setChannelService(channelService);
        messageService.setUserService(userService);

        //#################################################
        System.out.println("===채팅 서비스 테스트===\n");

        //유저 생성

        User user1 = new User("유저1","이메일1", "비번1");
        userService.create(user1);
        User user2 = new User("유저2","이메일2", "비번2");
        userService.create(user2);
        User user3 = new User("유저3","이메일3", "비번3");
        userService.create(user3);
        User user4 = new User("유저4","이메일4", "비번4");
        userService.create(user4);
        System.out.println("유저 생성 완료");

        //#################################################
        System.out.println("\n===유저 조회(단건) 테스트===\n");

        System.out.println(userService.read(user1.getId()));

        //#################################################
        System.out.println("\n===유저 조회(전체) 테스트===\n");

        userService.readAll().stream()
                .forEach(System.out::println);

        //#################################################
        System.out.println("\n===유저 수정 테스트===\n");
        user3.update("수정된 유저3", "수정된 이메일3","수정된 비번3");
        userService.save(user3);
        System.out.println("유저3 정보 수정 완료");
        System.out.println(userService.read(user1.getId()));

        //#################################################
        System.out.println("\n===유저 삭제 테스트===\n");
        userService.delete(user4.getId());
        System.out.println("유저4 삭제 완료");
        userService.readAll().stream()
                .forEach(System.out::println);

        //#################################################
        System.out.println("\n===채널 등록 테스트===\n");

        Channel channel1 = new Channel("채널1", user1.getId());
        channelService.create(channel1);

        channelService.addUserToChannel(user2.getId(),channel1.getId());
        channelService.addUserToChannel(user3.getId(),channel1.getId());

        //#################################################
        System.out.println("\n===메세지 등록 테스트===\n");

        Message message1 = new Message("유저1이 채널1에 작성한 메세지1", channel1.getId(), user1.getId());
        Message message2 = new Message("유저2이 채널1에 작성한 메세지2", channel1.getId(), user2.getId());
        Message message3 = new Message("유저3이 채널1에 작성한 메세지3", channel1.getId(), user3.getId());

        messageService.create(message1);
        messageService.create(message2);
        messageService.create(message3);

        for(Message message : messageService.readAll()){
            System.out.println(message.getContent());
        }
        //#################################################
        System.out.println("\n===메세지 작성한 유저 삭제 테스트===\n");

        userService.delete(user3.getId());
        System.out.println("유저3 삭제 완료");
        userService.readAll().stream()
                .forEach(System.out::println);
        for(Message message : messageService.readAll()){
            System.out.println(message.getContent());
        }
        //#################################################
        System.out.println("\n===채널 어드민 유저 삭제 테스트===\n");

        System.out.println("\n유저1 삭제");
        userService.delete(user1.getId());

        System.out.println("\n유저2 속한 채널");
        user2.getJoinedChannelId().stream()
                .map(channelId -> channelService.read(channelId).getChannelName())
                .forEach(System.out::println);

        for(Message message : messageService.readAll()){
            System.out.println(message.getContent());
        }
    }
}