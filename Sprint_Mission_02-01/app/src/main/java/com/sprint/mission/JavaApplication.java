package com.sprint.mission;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelUser;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelUserService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class JavaApplication {

    private static final JCFUserService userService = JCFUserService.getJcfUserService();
    private static final JCFChannelService channelService = JCFChannelService.getJcfChannelService();
    private static final JCFChannelUserService channelUserService = JCFChannelUserService.getJcfChannelUserService();
    private static final JCFMessageService messageService = JCFMessageService.getInstance();

    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null; // 현재 로그인한 사용자

    public static void main(String[] args) {
        
        System.out.println("=========================================");
        System.out.println("      DISCODEIT CONSOLE CHAT SYSTEM      ");
        System.out.println("=========================================");

        while (true) {
            try {
                if (currentUser == null) {
                    processAuth(); // 비로그인 상태 -> 로그인 화면
                } else {
                    processLobby(); // 로그인 상태 -> 대기실 화면
                }

            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
                e.printStackTrace(); // 디버깅용
            }
        }
    }

    private static void processAuth() {
        System.out.println("\n[1]로그인  [2]회원가입  [3]종료");
        System.out.print("선택>> ");
        String choice = scanner.nextLine();

        User user = null;

        switch (choice) {
            case "1": // 로그인
                System.out.print("ID: ");
                String loginId = scanner.nextLine();
                System.out.print("PW: ");
                String loginPw = scanner.nextLine();

                user = userService.login(loginId, loginPw);
                if (user != null) {
                    currentUser = user;
                    System.out.println(">> 환영합니다. " + user.getUsername() + "님");
                } else {
                    System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
                    // - 원래 아이디 비밀번호 중 뭐가 잘못되었는지 알려주면 안됨 -> 보안 사항
                }
                break;

            case "2": // 회원가입
                System.out.print("사용할 ID: ");
                String newId = scanner.nextLine();
                System.out.print("사용할 PW: ");
                String newPw = scanner.nextLine();

                user = userService.createUser(newId, newPw);
                if (user != null) {
                    System.out.println(">> 가입 완료! 로그인 해주세요.");
                } else {
                    System.out.println("가입 실패: 중복된 ID입니다.");
                }
                break;

            case "3":
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
            
            default:
                System.out.println("잘못된 입력입니다.");
        }
    }

    private static void processLobby() {
        System.out.println("\n-----------------------------------------");
        System.out.println("  [" + currentUser.getUsername() + "]님의 대기실");
        System.out.println("-----------------------------------------");

        // 내 채널 목록 보여주기
        List<ChannelUser> myChannelLinks = channelUserService.findAllByUserId(currentUser.getId());
        
        if (myChannelLinks.isEmpty()) {
            System.out.println("참여중인 채팅방이 없습니다.");
        } else {
            System.out.println("참여 중인 채널 목록");
            for (ChannelUser link : myChannelLinks) {
                Channel channel = channelService.readChannel(link.getChannelId());
                if (channel != null) {
                    System.out.printf("- %s (ID: %s)\n", channel.getName(), channel.getId());
                }
            }
        }

        System.out.println("\n[1]입장하기(ID입력)  [2]새 채널 만들기  [3]전체 채널 탐색하기  [4]마이페이지  [5]로그아웃");
        System.out.print("선택>> ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1": // 입장
                System.out.print("입장할 채널 ID(UUID) 입력: ");
                String inputUuid = scanner.nextLine();
                try {
                    UUID channelId = UUID.fromString(inputUuid);
                    enterChatRoom(channelId); // 채팅방 들어가기
                } catch (IllegalArgumentException e) {
                    System.out.println("! 올바르지 않은 UUID 형식입니다.");
                }
                break;

            case "2": // 생성
                System.out.print("방 제목: ");
                String title = scanner.nextLine();

                // 채널 생성
                Channel newChannel = channelService.createChannel(title, currentUser.getId());
                // - 생성 후 채널 - 유저 관계 매핑
                channelUserService.join(newChannel.getId(), currentUser.getId());

                System.out.println(">> 방이 생성되었습니다: " + newChannel.getName());
                System.out.println(">> ID: " + newChannel.getId()); // 복사해서 쓰라고 보여줌
                break;

            case "3": // 전체 목록 조회
                showAllChatRoom();
                break;

            case "4":
                processMyPage();
                break;

            case "5": // 로그아웃
                currentUser = null;
                System.out.println("로그아웃 되었습니다.");
                break;

            default:
                System.out.println("! 잘못된 입력입니다.");
        }
    }

    private static void processMyPage() {
        System.out.println("\n=== 마이페이지 ===");
        System.out.println("1. 닉네임 변경");
        System.out.println("2. 회원 탈퇴 (주의)");
        System.out.println("3. 뒤로 가기");
        System.out.print("선택>> ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("새로운 닉네임: ");
                String newName = scanner.nextLine();
                String oldName = currentUser.getUsername();
                userService.updateUserName(currentUser.getId(), newName);
                System.out.println("닉네임이 변경 되었습니다." + oldName + " -> " + newName);
                break;
            
            case "2":
                System.out.print("정말 탈퇴하시겠습니까? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    // 1. 내가장인 방들 다 폭파 (Cascade)
                    // (복잡해서 생략: 원래는 내가 방장인 방을 찾아서 다 지워야 함)
                    
                    // 2. 내 계정 삭제
                    userService.deleteUser(currentUser.getId());
                    currentUser = null; // 로그아웃 처리
                    System.out.println(">> 탈퇴가 완료되었습니다. 안녕히 가세요.");
                }
                break;

            case "3":
                break;
            
            default:
                System.out.println("!잘못된 입력입니다.");
        }
    }

    private static void showAllChatRoom() {
        List<Channel> channels = channelService.findAllChannelList();

        if (channels.isEmpty()) {
            System.out.println("현재 방이 없습니다.");
            return;
        }

        System.out.println("\n==== 전체 채널 리스트 ====\n");
        System.out.println("UUID | 채널 이름 | 현재 참여중인 인원 수");
        for (Channel channel : channels) {
            
            List<ChannelUser> channelUserList = channelUserService.findAllByChannelId(channel.getId());
            System.out.println(channel.getId() + " | " + channel.getName() + " | " + channelUserList.size());
        }

        System.out.print("참여하고 싶은 채널 UUID >> ");
        String input = scanner.nextLine();
        UUID inputUUID = UUID.fromString(input);

        enterChatRoom(inputUUID);
    }

    private static void enterChatRoom(UUID channelId) {
        // 현재 방이 있는지 확인
        Channel channel = channelService.readChannel(channelId);
        if (channel == null) {
            System.out.println("!존재하지 않는 방입니다.");
            return;
        }

        // 가입 여부 확인 (근데 그냥 자동으로 가입 처리)
        List<ChannelUser> myLinks = channelUserService.findAllByUserId(currentUser.getId());
        boolean isJoined = myLinks
                            .stream()
                            .anyMatch(link -> link.getChannelId().equals(channelId));
        if (!isJoined) { // 첫 방문
            System.out.println("처음 방문하셨군요! 채널에 입장(Join)합니다.");
            channelUserService.join(channelId, currentUser.getId());
        }

        // 3. 채팅 루프
        boolean inRoom = true;
        while (inRoom) {
            printChatScreen(channel); // 화면 그리기 - 채팅 치면 채팅 내용을 보여줘야함

            String input = scanner.nextLine();

            if (input.equals("/exit")) { // 채팅방 나가기 (뒤로가기)
                inRoom = false;
            } else if (input.equals("/leave")) { // 방 탈퇴
                channelUserService.leave(channelId, currentUser.getId());
                System.out.println("방을 탈퇴하셨습니다.");
                inRoom = false;
            } else if (input.equals("/delete")) { // 방 제거하기 - masterUser만 가sm
                if (currentUser.getId().equals(channel.getMasterUserId()))
                    deleteChannelProcess(channelId);
                else 
                    System.out.println("권한이 없습니다!!");
                inRoom = false;
            } else if (input.startsWith("/rename ")) {
                String newName = input.substring(8);
                if (currentUser.getId().equals(channel.getMasterUserId())) {
                    channelService.updateChannelName(channelId, newName);
                    System.out.println("방제목이 변경되었습니다.");
                } else {
                    System.out.println("권한이 없습니다.");
                }

            } else if (input.startsWith("/msg delete ")) {
                UUID msgUuid = UUID.fromString(input.substring(12));
                // 메세지 삭제 로직
                if (messageService.deleteMessage(msgUuid, currentUser.getId(), channelId)) {
                    System.out.println("메세지가 삭제되었습니다.");
                } else {
                    System.out.println("자신이 쓴 댓글만 지울 수 있습니다.");
                }

            } else if (input.startsWith("/msg update ")) {
                UUID msgUuid = UUID.fromString(input.substring(12));
                System.out.print("수정할 메세지 >> ");
                String modifyContent = scanner.nextLine();

                Message message = messageService.readMessage(msgUuid);
                
                if (message != null) {
                    message.getUserId().equals(currentUser.getId());
                } else {
                    return;
                }

                if (messageService.updateMessage(msgUuid, modifyContent) != null) {
                    System.out.println("메세지가 수정되었습니다.");
                } else {
                    System.out.println("자신이 쓴 댓글만 수정할 수 있습니다.");
                }

            } else if (!input.trim().isEmpty()) { // 공백은 전송 안되기ㅔ
                messageService.sendMessage(input, currentUser.getId(), channelId);
            }
        }
    }    

    private static void printChatScreen(Channel channel) {
        System.out.println("\n\n\n\n\n\n\n"); // 콘솔 청소 효과
        System.out.println("=========================================");
        System.out.println("  ROOM: " + channel.getName());
        System.out.println("  (명령어: /exit 뒤로가기, /leave 방퇴장, /delete 방삭제, /rename 방이름변경, /msg update 메세지수정, /msg delete 메세지삭제)");
        System.out.println("-----------------------------------------");
        
        // 1. 참여자 목록 출력
        List<ChannelUser> members = channelUserService.findAllByChannelId(channel.getId());
        System.out.print("[참여자 " + members.size() + "명]: ");
        for (ChannelUser member : members) {
            User u = userService.readUser(member.getUserId());
            if (u != null) System.out.print(u.getUsername() + " ");
        }
        System.out.println("\n-----------------------------------------");

        // 2. 메시지 기록 출력
        List<Message> logs = messageService.getMessages(channel.getId());
        if (logs.isEmpty()) {
            System.out.println("(메시지가 없습니다. 첫 인사를 건네보세요!)");
        } else {
            for (Message msg : logs) {
                User writer = userService.readUser(msg.getUserId());
                String writerName = (writer != null) ? writer.getUsername() : "(알수없음)";
                System.out.println(msg.getId() + " : " + writerName + ": " + msg.getContent());
            }
        }
        System.out.println("=========================================");
        System.out.print("메시지 입력>> ");
    }


    // 방 삭제
    private static void deleteChannelProcess(UUID channelId) {
        System.out.println(">> 방 삭제(폭파)를 시작합니다...");
        
        // 1. 메시지 삭제
        messageService.deleteAllByChannelId(channelId);
        // 2. 멤버 관계 삭제
        channelUserService.deleteAllByChannelId(channelId);
        // 3. 채널 삭제
        channelService.deleteChannel(channelId);
        
        System.out.println("방이 완전히 삭제되었습니다.");
    }

}

/* 테스트 해보다가 느낀 것
- 방장은 방을 leave할 수 없어야한다 -> 왜냐면 leave하면 연결이 끊어지고, 해당 방을 탐색은 할 수 있는데, 유령상태가 됨 -> 물론 방은 삭제할 수 있지만... 음? 방장이 없는 방이 되어버리는 느낌?

*/