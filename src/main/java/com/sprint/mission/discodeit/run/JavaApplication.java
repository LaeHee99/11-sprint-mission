package com.sprint.mission.discodeit.run;

import com.sprint.mission.discodeit.entity.Domain.Channel;
import com.sprint.mission.discodeit.entity.Domain.Message;
import com.sprint.mission.discodeit.entity.Domain.User;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.io.File;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        FileChannelService filechannelService = new FileChannelService(new FileChannelRepository());
        FileMessageService filemessageService = new FileMessageService(new FileMessageRepository());
        FileUserService fileuserService = new FileUserService(new FileUserRepository());

        System.out.println("=====================userTEST===============================================");
        UUID user1 = fileuserService.create(new User("정수용", "아하라마", "ONLINE"));
        UUID user2 = fileuserService.create(new User("백승준", "seungman", "OFFLINE"));
        System.out.println("전체 조회/생성 테스트");
        System.out.println(fileuserService.readAll());

        System.out.println("user1 단건 조회");
        System.out.println(fileuserService.read(user1));

        fileuserService.update(user1, "정수용 업데이트", "아하라마 업데이트", "ONLINE");
        System.out.println("업데이트 테스트");
        System.out.println(fileuserService.read(user1));

        fileuserService.delete(user2);
        System.out.println("user2 삭제테스트/ 전체 조회시 user1만 있어야함");
        System.out.println(fileuserService.readAll());
        System.out.println("=====================channelTEST===============================================");
        UUID channel1 = filechannelService.create(new Channel("정수용님의 채널", "aaaaaaaaaaaaaaa"));
        UUID channel2 = filechannelService.create(new Channel("백승준님의 채널", "ccccccccccccc"));
        System.out.println("전체 조회/생성 테스트");
        System.out.println(filechannelService.readAll());

        System.out.println("channel1 단건 조회");
        System.out.println(filechannelService.read(channel1));

        filechannelService.update(channel1, "채널 업데이트", "채널주소 업데이트");
        System.out.println("업데이트 테스트");
        System.out.println(filechannelService.read(channel1));

        filechannelService.delete(channel2);
        System.out.println("channel2 삭제 테스트/ 전체 조회 시 channel2가 없어야함");
        System.out.println(filechannelService.readAll());


        System.out.println("=====================messageTEST===============================================");
        UUID message1 = filemessageService.create(new Message("보내는 내용","보내는이", "받는이" ));
        UUID message2 = filemessageService.create(new Message("send message", "sender", "receiver"));
        System.out.println("전체 조회/생성 테스트");
        System.out.println(filemessageService.readAll());

        System.out.println("message1 단건 조회");
        System.out.println(filemessageService.read(message1));

        filemessageService.update(message1, "업데이트된 메세지");
        System.out.println("업데이트 테스트");
        System.out.println(filemessageService.read(message1));

        filemessageService.delete(message2);
        System.out.println("message2 삭제 테스트/ 전체 조회시 message2가 없어야함");
        System.out.println(filemessageService.readAll());


//        JCFUserService userService = new JCFUserService();
//        JCFChannelService filechannelService = new JCFChannelService();
//        JCFMessageService filemessageService = new JCFMessageService();

//        System.out.println("=====================userTEST===============================================");
//        UUID user1 = userService.create(new User("정수용", "아하라마", "ONLINE"));
//        UUID user2 = userService.create(new User("백승준", "seungman", "OFFLINE"));
//        System.out.println("전체 조회/생성 테스트");
//        System.out.println(userService.readAll());
//
//        System.out.println("user1 단건 조회");
//        System.out.println(userService.read(user1));
//
//        userService.update(user1, "정수용 업데이트", "아하라마 업데이트", "ONLINE");
//        System.out.println("업데이트 테스트");
//        System.out.println(userService.read(user1));
//
//        userService.delete(user2);
//        System.out.println("user2 삭제 테스트/ 전체 조회시 user2가 없어야함");
//        System.out.println(userService.readAll());
//
//        System.out.println("=====================channelTEST===============================================");
//        UUID channel1 = channelService.create(new Channel("정수용님의 채널", "aaaaaaaaaaaaa"));
//        UUID channel2 = channelService.create(new Channel("백승준님의 채널", "ccccccccccccc"));
//        System.out.println("전체 조회/생성 테스트");
//        System.out.println(userService.readAll());
//
//        System.out.println("channel1 단건 조회");
//        System.out.println(channelService.read(channel1));
//
//        channelService.update(channel1, "채널 업데이트", "채널주소 업데이트");
//        System.out.println("업데이트 테스트");
//        System.out.println(channelService.read(channel1));
//
//        channelService.delete(channel2);
//        System.out.println("channel2 삭제 테스트/ 전체 조회 시 channel2가 없어야함");
//        System.out.println(channelService.readAll());
//
//        System.out.println("=====================messageTEST===============================================");
//        UUID message1 = messageService.create(new Message("보내는 내용","보내는이", "받는이" ));
//        UUID message2 = messageService.create(new Message("send message", "sender", "receiver"));
//        System.out.println("전체 조회/생성 테스트");
//        System.out.println(messageService.readAll());
//
//        System.out.println("message1 단건 조회");
//        System.out.println(messageService.read(message1));
//
//        messageService.update(message1, "업데이트된 메세지");
//        System.out.println("업데이트 테스트");
//        System.out.println(messageService.read(message1));
//
//        messageService.delete(message2);
//        System.out.println("message2 삭제 테스트/ 전체 조회시 message2가 없어야함");
//        System.out.println(messageService.readAll());
    }
}