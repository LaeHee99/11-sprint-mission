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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) {
        restart(); // 시작할때 파일 초기화

        FileChannelService filechannelService = new FileChannelService(new FileChannelRepository());
        FileMessageService filemessageService = new FileMessageService(new FileMessageRepository());
        FileUserService fileuserService = new FileUserService(new FileUserRepository());

        System.out.println("=====================[userTEST]===============================================");
        UUID user1 = fileuserService.create(new User("정수용", "아하라마", "ONLINE"));
        UUID user2 = fileuserService.create(new User("백승준", "seungman", "OFFLINE"));
        System.out.println("[전체 조회/생성 테스트]");
        fileuserService.readAll()
                        .forEach(System.out::println);

        System.out.println("\n[user1 단건 조회]");
        System.out.println(fileuserService.read(user1));

        fileuserService.update(user1, "정수용 업데이트", "아하라마 업데이트", "ONLINE");
        System.out.println("\n[업데이트 테스트]");
        System.out.println(fileuserService.read(user1));

        fileuserService.delete(user2);
        System.out.println("\n[user2 삭제테스트/ 전체 조회시 user1만 있어야함]");
        System.out.println(fileuserService.readAll());
        System.out.println();
        System.out.println("=====================channelTEST===============================================");
        UUID channel1 = filechannelService.create(new Channel("정수용님의 채널", "aaaaaaaaaaaaaaa"));
        UUID channel2 = filechannelService.create(new Channel("백승준님의 채널", "ccccccccccccc"));
        System.out.println("[전체 조회/생성 테스트]");
        filechannelService.readAll()
                        .forEach(System.out::println);

        System.out.println("\n[channel1 단건 조회]");
        System.out.println(filechannelService.read(channel1));

        filechannelService.update(channel1, "채널 업데이트", "채널주소 업데이트");
        System.out.println("\n[업데이트 테스트]");
        System.out.println(filechannelService.read(channel1));

        filechannelService.delete(channel2);
        System.out.println("\n[channel2 삭제 테스트/ 전체 조회 시 channel2가 없어야함]");
        System.out.println(filechannelService.readAll());
        System.out.println();

        System.out.println("=====================messageTEST===============================================");
        UUID message1 = filemessageService.create(new Message("보내는 내용", "보내는이", "받는이"));
        UUID message2 = filemessageService.create(new Message("send message", "sender", "receiver"));
        System.out.println("[전체 조회/생성 테스트]");
        filemessageService.readAll()
                .forEach(System.out::println);

        System.out.println("\n[message1 단건 조회]");
        System.out.println(filemessageService.read(message1));

        filemessageService.update(message1, "업데이트된 메세지");
        System.out.println("\n[업데이트 테스트]");
        System.out.println(filemessageService.read(message1));

        filemessageService.delete(message2);
        System.out.println("\n[message2 삭제 테스트/ 전체 조회시 message2가 없어야함]");
        System.out.println(filemessageService.readAll());
        System.out.println();
        System.out.println("=====================User 예외TEST===============================================");
        testUserValidation(fileuserService);
        testChannelValidation(filechannelService);
        testMessageValidation(filemessageService);
    }

    private static void testUserValidation(FileUserService userService) {
        System.out.print("\n=====[User 예외 처리 테스트]=====");
        // (1) 중복 유저명 테스트
        try {
            System.out.println("\n[중복 유저명 테스트]");
            User u = new User("중복유저테스트", "nick", "ONLINE");
            UUID id1 = userService.create(u);
            System.out.println("첫 생성 성공: " + userService.read(id1));

            // 같은 이름으로 다시 생성 → IllegalArgumentException 예상
            userService.create(new User("중복유저테스트", "다른닉", "OFFLINE"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }
        // (2) null 유저명 테스트
        try {
            System.out.println("\n[null 유저명 테스트]");
            userService.create(new User(null, "nick", "ONLINE"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }
        // (3) blank 유저명 테스트
        try {
            System.out.println("\n[blank 유저명 테스트]");
            userService.create(new User("   ", "nick", "ONLINE"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }
        System.out.println("===== [User 예외 처리 테스트 끝] =====\n");
    }

    private static void testChannelValidation(FileChannelService channelService) {
        System.out.print("\n======[Channel 예외 처리 테스트============");
        // (1) 중복 채널명 테스트
        try {
            System.out.println("\n[중복 채널명 테스트]");
            Channel c = new Channel("중복채널테스트", "ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
            UUID id2 = channelService.create(c);
            System.out.println("첫 생성 성공: " + channelService.read(id2));
            // 같은 이름으로 다시 생성 → IllegalArgumentException 예상
            channelService.create(new Channel("중복채널테스트", "ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");

        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }
        // (2) null 채널 테스트
        try {
            System.out.println("\n[null 채널명 테스트]");
            channelService.create(new Channel(null, "aaaaaaaaa"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }

        // (3) blank 채널 테스트
        try {
            System.out.println("\n[blank 채널명 테스트]");
            channelService.create(new Channel("   ", "aaaaaaaaaaaa"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }
        System.out.println("===== [Channel 예외 처리 테스트 끝] =====\n");
    }

    private static void testMessageValidation(FileMessageService messageService) {
        System.out.print("\n======[Message 예외 처리 테스트============");
        // (1) null 메세지 테스트
        try {
            System.out.println("\n[null 메세지 테스트]");
            messageService.create(new Message(null, "sender", "receiver"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }

        // (2) blank 메세지 테스트
        try {
            System.out.println("\n[blank 채널명 테스트]");
            messageService.create(new Message("   ", "sender", "receiver"));
            System.out.println("여기까지 오면 안 됨 (예외가 안 난 것)");
        } catch (IllegalArgumentException e) {
            System.out.println("예상대로 예외 발생: " + e.getMessage());
        }
        System.out.println("===== [Message 예외 처리 테스트 끝] =====\n");
    }

    private static void restart() {
        try {
            Files.deleteIfExists(Path.of("users.ser"));
            Files.deleteIfExists(Path.of("Channel.ser"));
            Files.deleteIfExists(Path.of("Message.ser"));
        } catch (IOException e) {
            throw new RuntimeException("데이터 초기화에 실패했습니다.", e);
        }
    }
}







