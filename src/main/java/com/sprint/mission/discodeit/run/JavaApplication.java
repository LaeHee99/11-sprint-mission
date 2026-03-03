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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class JavaApplication {
    public static void main(String[] args) throws IOException {
//        restart(); // 시작할 때 파일 초기화 (원할때 주석제거해서 초기화하기)

        FileChannelService filechannelService = new FileChannelService(new FileChannelRepository());
        FileMessageService filemessageService = new FileMessageService(new FileMessageRepository());
        FileUserService fileuserService = new FileUserService(new FileUserRepository());

        // 헷갈리는거 정리해놓기
        // FileUserService, FileChannelService, FileMessageService  에다가 .create, .read, .readAll, .update, .delete하기
        // user     -   FileUserService.create(new User(이름, 닉네임, 온오프라인))/ .read(id) / .readAll - stream쓰면 배열로 출력안됨
        //              .update(id, 이름, 닉네임, 온오프라인) / .delete(id)임
        // channel  -   FileChannelService.create(new Channel(채널명, 채널주소)) / .read(id) / .readAll /
        //              .update(id, 채널명, 채널주소) / .delete(id)
        // message  -   FileMessageService.create(new Message(내용, 보낸이, 받는이)) / .read(id) / .readAll /
        //              .update(id, 내용) / .delete(id)


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        // TODO
        // UUID 목록 기억안나니까 출력하나 만들기 -- O
        while (true) {      // while 안에서 user, channel, message 각각 만들기 > 그래야 break; 했을때 돌아옴.
            System.out.println("======================================");
            System.out.println("원하는 기능을 선택하세요");
            System.out.println("1. User");
            System.out.println("2. Channel");
            System.out.println("3. Message");
            System.out.println("4. 저장된 UUID목록");
            System.out.println("5. Exit");
            System.out.print("선택: ");

            String input = br.readLine();
            if (input == null) continue;
            input = input.trim();

            if (input.equals("1")) {
                handleUserMenu(br, fileuserService);
            } else if (input.equals("2")) {
                handleChannelMenu(br, filechannelService);
            } else if (input.equals("3")) {
                handleMessageMenu(br, filemessageService);
            } else if (input.equals("4")) { //UUID 목록
                printAllIds(fileuserService, filechannelService, filemessageService);
            } else if (input.equals("5")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다. 다시 선택하세요.");
            }
        }
    }

    private static void restart() {     // 실행할때마다 초기화 시키기
        try {
            Files.deleteIfExists(Path.of("users.ser"));
            Files.deleteIfExists(Path.of("Channel.ser"));
            Files.deleteIfExists(Path.of("Message.ser"));
        } catch (IOException e) {
            throw new RuntimeException("데이터 초기화에 실패했습니다.", e);
        }
    }

    private static void handleUserMenu(BufferedReader br, FileUserService userService) throws IOException {
        while (true) {
            System.out.println("----- [User 메뉴] -----");
            System.out.println("1. User 생성");
            System.out.println("2. User 단건 조회");
            System.out.println("3. User 전체 조회");
            System.out.println("4. User 수정");
            System.out.println("5. User 삭제");
            System.out.println("6. User UUID 목록");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = br.readLine();
            if (input == null) continue;    // null이면 다시 물어보기
            input = input.trim();           // 공백 없이 입력받기

            try {
                if (input.equals("1")) { // create
                    System.out.print("이름: ");
                    String name = br.readLine();
                    System.out.print("닉네임: ");
                    String nickname = br.readLine();
                    System.out.print("상태(ONLINE/OFFLINE 등): ");
                    String status = br.readLine();

                    UUID id = userService.create(new User(name, nickname, status));
                    System.out.println("생성 완료. ID = " + id);

                } else if (input.equals("2")) { // read
                    System.out.print("조회할 User ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);   // UUID를 String 형으로 입력받기
                    User user = userService.read(id);
                    System.out.println(user != null ? user : "해당 ID의 User가 없습니다."); // null아니면 user, null이면 문자 반환

                } else if (input.equals("3")) { // readAll
                    System.out.println("[User 전체 조회]");
                    userService.readAll().forEach(System.out::println); // 배열로 출력되니까 안이쁘네..

                } else if (input.equals("4")) { // update
                    System.out.print("수정할 User ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);

                    System.out.print("새 이름: ");
                    String name = br.readLine();
                    System.out.print("새 닉네임: ");
                    String nickname = br.readLine();
                    System.out.print("새 상태: ");
                    String status = br.readLine();

                    userService.update(id, name, nickname, status);
                    System.out.println("수정 완료.");
                    System.out.println("수정 결과: " + userService.read(id));

                } else if (input.equals("5")) { // delete
                    System.out.print("삭제할 User ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);
                    userService.delete(id);
                    System.out.println("삭제 완료.");

                } else if (input.equals("6")) {
                    printUser(userService);
                } else if (input.equals("0")) { // back
                    System.out.println("User 메뉴를 종료합니다.");
                    break;

                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("예외 발생: null, blank 불가");
            } catch (Exception e) {
                System.out.println("알 수 없는 오류: " + e.getMessage());
            }
        }
    }

    private static void handleChannelMenu(BufferedReader br, FileChannelService channelService) throws IOException {
        while (true) {
            System.out.println("----- [Channel 메뉴] -----");
            System.out.println("1. Channel 생성");
            System.out.println("2. Channel 단건 조회");
            System.out.println("3. Channel 전체 조회");
            System.out.println("4. Channel 수정");
            System.out.println("5. Channel 삭제");
            System.out.println("6. Channel UUID 목록");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = br.readLine();
            if (input == null) continue;
            input = input.trim();

            try {
                if (input.equals("1")) { // create
                    System.out.print("채널 이름: ");
                    String name = br.readLine();
                    System.out.print("채널 설명: ");
                    String desc = br.readLine();

                    UUID id = channelService.create(new Channel(name, desc));
                    System.out.println("생성 완료. ID = " + id);

                } else if (input.equals("2")) { // read
                    System.out.print("조회할 Channel ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);
                    Channel channel = channelService.read(id);
                    System.out.println(channel != null ? channel : "해당 ID의 Channel이 없습니다.");

                } else if (input.equals("3")) { // readAll
                    System.out.println("[Channel 전체 조회]");
                    channelService.readAll().forEach(System.out::println);

                } else if (input.equals("4")) { // update
                    System.out.print("수정할 Channel ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);

                    System.out.print("새 채널 이름: ");
                    String name = br.readLine();
                    System.out.print("새 채널 설명: ");
                    String desc = br.readLine();

                    channelService.update(id, name, desc);
                    System.out.println("수정 완료.");
                    System.out.println("수정 결과: " + channelService.read(id));

                } else if (input.equals("5")) { // delete
                    System.out.print("삭제할 Channel ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);
                    channelService.delete(id);
                    System.out.println("삭제 완료.");

                }else if (input.equals("6")) {
                    printChannel(channelService);
                } else if (input.equals("0")) { // back
                    System.out.println("Channel 메뉴를 종료합니다.");
                    break;

                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("예외 발생: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 오류: " + e.getMessage());
            }
        }
    }

    private static void handleMessageMenu(BufferedReader br, FileMessageService messageService) throws IOException {
        while (true) {
            System.out.println("----- [Message 메뉴] -----");
            System.out.println("1. Message 생성");
            System.out.println("2. Message 단건 조회");
            System.out.println("3. Message 전체 조회");
            System.out.println("4. Message 수정");
            System.out.println("5. Message 삭제");
            System.out.println("6. Message UUID 목록");
            System.out.println("0. 뒤로가기");
            System.out.print("선택: ");

            String input = br.readLine();
            if (input == null) continue;
            input = input.trim();

            try {
                if (input.equals("1")) { // create
                    System.out.print("내용: ");
                    String content = br.readLine();
                    System.out.print("보내는이: ");
                    String sender = br.readLine();
                    System.out.print("받는이: ");
                    String receiver = br.readLine();

                    UUID id = messageService.create(new Message(content, sender, receiver));
                    System.out.println("생성 완료. ID = " + id);

                } else if (input.equals("2")) { // read
                    System.out.print("조회할 Message ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);
                    Message message = messageService.read(id);
                    System.out.println(message != null ? message : "해당 ID의 Message가 없습니다.");

                } else if (input.equals("3")) { // readAll
                    System.out.println("[Message 전체 조회]");
                    messageService.readAll().forEach(System.out::println);

                } else if (input.equals("4")) { // update
                    System.out.print("수정할 Message ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);

                    System.out.print("새 내용: ");
                    String content = br.readLine();

                    messageService.update(id, content);
                    System.out.println("수정 완료.");
                    System.out.println("수정 결과: " + messageService.read(id));

                } else if (input.equals("5")) { // delete
                    System.out.print("삭제할 Message ID(UUID): ");
                    String idStr = br.readLine();
                    UUID id = UUID.fromString(idStr);
                    messageService.delete(id);
                    System.out.println("삭제 완료.");

                }else if (input.equals("6")) {
                    printMessage(messageService);
                } else if (input.equals("0")) { // back
                    System.out.println("Message 메뉴를 종료합니다.");
                    break;

                } else {
                    System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("예외 발생: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("알 수 없는 오류: " + e.getMessage());
            }
        }
    }

    private static void printAllIds(FileUserService userService,
                                    FileChannelService channelService,
                                    FileMessageService messageService) {
        System.out.println("===== [현재 저장된 전체 ID 목록] =====");

        System.out.println("\n[User ID]");
        userService.readAll().forEach(u ->
                System.out.println("id=" + u.getId() + " | name=" + u.getUserName())
        );

        System.out.println("\n[Channel ID]");
        channelService.readAll().forEach(c ->
                System.out.println("id=" + c.getId() + " | name=" + c.getChannelName())
        );

        System.out.println("\n[Message ID]");
        messageService.readAll().forEach(m ->
                System.out.println("id=" + m.getId() + " | sender=" + m.getSender() + " | receiver=" + m.getReceiver())
        );
        System.out.println("====================================\n");
    }

    public static void printUser(FileUserService userService){
        System.out.println("==== [현재 저장된 USER ID 목록] ====");
        System.out.println("\n[User ID]");
        userService.readAll().forEach(u ->
                System.out.println("id=" + u.getId() + " | name=" + u.getUserName())
        );
        System.out.println("====================================\n");
    }

    public static void printChannel(FileChannelService channelService){
        System.out.println("==== [현재 저장된 CHANNEL ID 목록] ====");
        System.out.println("\n[Channel ID]");
        channelService.readAll().forEach(c ->
                System.out.println("id=" + c.getId() + " | name=" + c.getChannelName())
        );
        System.out.println("====================================\n");
    }

    public static void printMessage(FileMessageService messageService){
        System.out.println("==== [현재 저장된 Message ID 목록] ====");
        System.out.println("\n[Message ID]");
        messageService.readAll().forEach(m ->
                System.out.println("id=" + m.getId() + " | sender=" + m.getSender() + " | receiver=" + m.getReceiver())
        );
        System.out.println("====================================\n");
    }
}




