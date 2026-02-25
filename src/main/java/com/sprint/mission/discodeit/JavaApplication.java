package com.sprint.mission.discodeit;

import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFMessageService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import java.util.Scanner;



public class JavaApplication {
    public static void main(String[] args){
        UserService userService = new JCFUserService();
        ChannelService channelService = new JCFChannelService();
        MessageService messageService = new JCFMessageService();


        //한 4명정도 아이디 받아보기.
        Scanner scanner = new Scanner(System.in);
        User[] users = new User[4];
        UUID[] ids = new UUID[4];

        for(int i = 0; i<4; i++){
            System.out.println((i+1) + "번재 유저의 이름 입력: ");
            String name = scanner.nextLine().trim();
            users[i] = userService.create(new User(name));
            ids[i] = users[i].getId();
        }
        for (int i = 0; i < 4; i++) {
            System.out.println((i + 1) + "번째 유저: " + userService.findById(ids[i]));
        }

        //ㅇ
        System.out.println("\n===== 현재 등록된 전체 유저 목록 =====");
        List<User> allUsers = userService.findAll();

        if (allUsers.isEmpty()) {
            System.out.println("현재 등록된 유저가 없습니다.");
        } else {
            allUsers.forEach(user ->
                    System.out.println("[ID: " + user.getId() + "] 이름: " + user.getUser())
            );
        }

        //유저 조회하고 싶을때
        System.out.print("\n조회할 유저 ID 입력: ");
        try {
            UUID searchId = UUID.fromString(scanner.nextLine().trim());

            userService.findById(searchId).ifPresentOrElse(
                    user -> System.out.println("조회 결과 -> 이름: " + user.getUser() + " (ID: " + user.getId() + ")"),
                    () -> System.out.println("해당 ID의 유저를 찾을 수 없습니다.")
            );
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 ID 형식입니다.");
        }
        //유저 삭제하고 싶을때
        System.out.println("삭제하고 싶은 유저의 이름을 적어주세요");
        String removeName = scanner.nextLine().trim();
        List<User> matches = userService.findAll().stream()
                .filter(u -> u.getUser().equals(removeName))
                .collect(Collectors.toList());
        if(matches.isEmpty()){
            System.out.println("매치되는 유저가 없습니다.");
            return;
        }
        for(User u : matches){
            System.out.println("삭제여부" +userService.delete(u.getId()));
        }

        //삭제확인
        System.out.print("\n삭제 확인을 위한 유저 ID 입력: ");
        try {
            UUID checkId = UUID.fromString(scanner.nextLine().trim());

            boolean isPresent = userService.findById(checkId).isPresent();

            if (!isPresent) {
                System.out.println("확인 완료: 해당 유저가 데이터베이스에서 정상적으로 삭제되었습니다.");
            } else {
                System.out.println("경고: 유저가 아직 남아있습니다. 삭제 로직을 확인하세요.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("잘못된 ID 형식입니다.");
        }

        //유저 아이디 받아서 바꿔보기
        try {
            System.out.print("수정할 유저 id(UUID) 입력: ");
            UUID id = UUID.fromString(scanner.nextLine().trim());

            userService.findById(id).ifPresentOrElse(user -> {
                System.out.print("새 이름 입력: ");
                String newName = scanner.nextLine().trim();
                user.setUpdate(newName);
                userService.update(user);
            }, () -> System.out.println("존재하지 않는 ID입니다."));

        } catch (IllegalArgumentException e) {
            System.out.println("유효하지 않은 UUID 형식입니다. 수정을 건너뜁니다.");
        }












    }
}
