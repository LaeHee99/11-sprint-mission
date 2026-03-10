package com.sprint.mission;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelMember;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.ChannelMemberRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelMemberRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.file.FileChannelMemberService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;
import com.sprint.mission.discodeit.service.ChannelMemberService;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

public class JavaApplication {
    
    private static ChannelMemberRepository channelMemberRepository = FileChannelMemberRepository.getInstance();
    private static ChannelMemberService channelMemberService = FileChannelMemberService.getInstance(channelMemberRepository);

    private static UserRepository userRepository = FileUserRepository.getInstance();
    private static UserService userService = FileUserService.getInstance(userRepository, channelMemberService);

    private static ChannelRepository channelRepository = FileChannelRepository.getInstance();
    private static ChannelService channelService = FileChannelService.getInstance(channelRepository, channelMemberService);

    private static MessageRepository messageRepository = FileMessageRepository.getInstance();
    private static MessageService messageService = FileMessageService.getInstance(messageRepository, channelMemberService);


    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null; // 현재 로그인한 사용자

    public static void main(String[] args) {
        while (true) {
            try {
                if (currentUser == null) {
                    processAuth();
                } else {
                    processLobby();
                }
            } catch (Exception e) {
                System.out.println("\n❌ 오류 발생: " + e.getMessage());
                pause(); // 에러 메시지를 읽을 수 있도록 잠시 멈춤
            }
        }
    }

    // ==========================================
    // UI 헬퍼 메서드 (화면 정리 및 헤더 출력)
    // ==========================================
    private static void clearScreen() {
        // 터미널 화면을 지우는 ANSI 이스케이프 코드
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printHeader(String title) {
        clearScreen();
        System.out.println("===============================================================");
        System.out.println("  " + title);
        System.out.println("===============================================================");
    }

    private static void pause() {
        System.out.print("\n[엔터 키를 누르면 돌아갑니다...]");
        scanner.nextLine();
    }
    // ==========================================

    private static void processAuth() {
        printHeader("DISCODEIT CONSOLE CHAT SYSTEM");
        System.out.println("\n  [1] 로그인\n  [2] 회원가입\n  [3] 종료\n");
        System.out.print("▶ 선택: ");

        String choice = scanner.nextLine();

        // Controller -> Service ()

        switch (choice) {
            case "1":
                printHeader("로 그 인");
                System.out.print("▶ ID: ");
                String loginId = scanner.nextLine();
                System.out.print("▶ PW: ");
                String loginPw = scanner.nextLine();

                currentUser = userService.login(loginId, loginPw);
                System.out.println("\n✅ 로그인에 성공하였습니다.");
                System.out.println("환영합니다, [" + currentUser.getUsername() + "]님!");
                pause();
                break;

            case "2":
                printHeader("회 원 가 입");
                System.out.print("▶ 생성할 ID: ");
                String newId = scanner.nextLine();
                System.out.print("▶ 생성할 PW: ");
                String newPw = scanner.nextLine();

                userService.register(newId, newPw);
                System.out.println("\n✅ 가입에 성공하였습니다.");
                pause();
                break;

            case "3":
                System.out.println("\n프로그램을 종료합니다. 안녕히 가세요!");
                System.exit(0);
                break;

            default:
                throw new RuntimeException("잘못된 입력입니다.");
        }
    }

    private static void processLobby() {
        if (currentUser == null) return;

        printHeader("[" + currentUser.getUsername() + "]님의 대기실");
        System.out.println("\n  [1] 가입한 채널 목록");
        System.out.println("  [2] 전체 채널 탐색");
        System.out.println("  [3] 새 채널 생성");
        System.out.println("  [4] 마이페이지");
        System.out.println("  [5] 로그아웃\n");
        System.out.print("▶ 선택: ");

        String choice = scanner.nextLine();

        switch (choice) {
            case "1": showJoinedChannelList(); break;
            case "2": showAllChannelList(); break;
            case "3": showCreateChannel(); break;
            case "4": showMyPage(); break;
            case "5":
                currentUser = null;
                System.out.println("\n✅ 로그아웃 되었습니다.");
                pause();
                break;
            default:
                throw new RuntimeException("잘못된 입력입니다.");
        }
    }

    private static void showJoinedChannelList() {
        printHeader("가입한 채널 목록");
        System.out.printf("%-36s | %-15s | %s\n", "채널 UUID", "채널명", "유저수");
        System.out.println("---------------------------------------------------------------");
        
        List<ChannelMember> list = channelMemberService.getChannels(currentUser.getId());
        for (ChannelMember cm : list) {
            Channel channel = channelService.getChannel(cm.getChannelId());
            System.out.printf("%-36s | %-15s | %d명\n", 
                channel.getId(), 
                channel.getName(), 
                channelMemberService.getMembers(channel.getId()).size());
        }

        System.out.println("\n  [1] 입장하기");
        System.out.println("  [2] 뒤로가기\n");
        System.out.print("▶ 선택: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("▶ 입장할 채널 UUID: ");
                UUID channelId = UUID.fromString(scanner.nextLine());
                enterChannel(channelId);
                break;
            case "2":
                break;
            default:
                System.out.println("❌ 잘못된 입력입니다.");
                pause();
        }
    }

    private static void showAllChannelList() {
        printHeader("전체 채널 탐색");
        System.out.printf("%-36s | %-15s | %s\n", "채널 UUID", "채널명", "유저수");
        System.out.println("---------------------------------------------------------------");
        
        List<Channel> list = channelService.getAllChannels();
        for (Channel channel : list) {
            System.out.printf("%-36s | %-15s | %d명\n", 
                channel.getId(), 
                channel.getName(), 
                channelMemberService.getMembers(channel.getId()).size());
        }

        System.out.println("\n  [1] 가입하기");
        System.out.println("  [2] 뒤로가기\n");
        System.out.print("▶ 선택: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("▶ 가입할 채널 UUID: ");
                UUID channelId = UUID.fromString(scanner.nextLine());
                
                channelService.joinChannel(channelId, currentUser.getId());
                System.out.println("\n✅ 채널에 가입되었습니다! '가입한 채널' 목록에서 입장해주세요.");
                pause();
                break;
            case "2":
                break;
            default:
                System.out.println("❌ 잘못된 입력입니다.");
                pause();
        }
    }

    private static void showCreateChannel() {
        printHeader("새 채널 생성");
        System.out.print("▶ 만들고 싶은 채널명: ");
        String channelName = scanner.nextLine();
        
        channelService.createChannel(channelName, currentUser.getId());
        System.out.println("\n✅ [" + channelName + "] 채널이 생성되었습니다.");
        pause();
    }

    private static void showMyPage() {
        printHeader("마 이 페 이 지");
        System.out.println("  내 ID : " + currentUser.getUsername());
        System.out.println("  내 PW : " + currentUser.getPassword());
        System.out.println("---------------------------------------------------------------");

        System.out.println("\n  [1] 회원 탈퇴");
        System.out.println("  [2] 뒤로가기\n");
        System.out.print("▶ 선택: ");
        
        String choice = scanner.nextLine();
        switch (choice) {
            case "1":
                System.out.print("정말 탈퇴하시겠습니까? (Y/N): ");
                String confirm = scanner.nextLine();
                if (confirm.equalsIgnoreCase("Y")) {
                    userService.deleteUser(currentUser.getId());
                    System.out.println("\n✅ 회원 탈퇴가 완료되었습니다.");
                    currentUser = null;
                    pause();
                }
                break;
            case "2":
                break;
            default:
                System.out.println("❌ 잘못된 입력입니다.");
                pause();
        }
    }

    private static void enterChannel(UUID channelId) {
        Channel currentChannel = channelRepository.findById(channelId)
            .orElseThrow(() -> new RuntimeException("그런 채널 없습니다."));

        while (true) {
            // 채팅방은 메시지가 추가/수정/삭제될 때마다 화면을 리프레시합니다.
            printHeader("💬 " + currentChannel.getName() + " 채널 (UUID: " + currentChannel.getId() + ")");
            
            List<Message> messageList = messageService.getMessages(channelId, currentUser.getId());
            
            if (messageList.isEmpty()) {
                System.out.println("  (아직 작성된 메시지가 없습니다. 첫 메시지를 남겨보세요!)");
            } else {
                for (Message message : messageList) {
                    String writeName = userRepository.findById(message.getUserId())
                        .map(User::getUsername)
                        .orElse("(알 수 없음)");
                    
                    // 가독성을 위해 메시지 식별용 UUID의 앞 8자리만 보여줍니다.
                    String shortMsgId = message.getId().toString().substring(0, 8);
                    System.out.printf("[%s] %s : %s  (등록:%s, 수정:%s)\n", 
                        shortMsgId, writeName, message.getContent(), message.getCreatedAt(), message.getUpdatedAt());
                }
            }

            System.out.println("---------------------------------------------------------------");
            System.out.println("💡 명령어 안내 : /exit (나가기), /leave (채널탈퇴), /update [메시지ID앞8자리], /delete [메시지ID앞8자리]");
            System.out.print("\n▶ 메시지 입력: ");
            String input = scanner.nextLine();

            // 아무것도 입력하지 않은 경우 방지
            if (input.trim().isEmpty()) continue;

            try {
                if (input.startsWith("/exit")) {
                    break;
                } else if (input.startsWith("/leave")) {
                    System.out.print("정말 채널에서 탈퇴하시겠습니까? (Y/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("Y")) {
                        channelMemberService.leaveChannel(channelId, currentUser.getId());
                        System.out.println("✅ 채널을 탈퇴했습니다.");
                        pause();
                        break;
                    }
                } else if (input.startsWith("/update ")) {
                    // 메시지 수정 (앞 8자리 식별자로 전체 UUID를 찾아 매핑)
                    String shortId = input.substring(8).trim();
                    UUID fullMsgId = findMessageIdByShortId(messageList, shortId);
                    
                    System.out.print("▶ 수정할 내용: ");
                    String newContent = scanner.nextLine();
                    messageService.updateMessage(fullMsgId, currentUser.getId(), newContent);

                } else if (input.startsWith("/delete ")) {
                    // 메시지 삭제
                    String shortId = input.substring(8).trim();
                    UUID fullMsgId = findMessageIdByShortId(messageList, shortId);
                    
                    messageService.deleteMessage(fullMsgId, currentUser.getId());
                } else {
                    // 일반 메시지 전송
                    messageService.sendMessage(channelId, currentUser.getId(), input);
                }
            } catch (Exception e) {
                System.out.println("\n❌ 처리 실패: " + e.getMessage());
                pause();
            }
        }
    }

    // UUID 전체를 입력하기 힘들기 때문에 앞 8자리만으로 메시지를 찾는 헬퍼 로직 추가 (논리에 영향 주지 않음)
    private static UUID findMessageIdByShortId(List<Message> messageList, String shortId) {
        return messageList.stream()
                .filter(m -> m.getId().toString().startsWith(shortId))
                .findFirst()
                .map(Message::getId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 메시지를 찾을 수 없습니다: " + shortId));
    }
}