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


        //유저 이름 받아서 바꿔보기
        System.out.print("수정할 유저 id(UUID) 입력: ");
        UUID id = UUID.fromString(scanner.nextLine().trim());

        User u = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("없음"));

        System.out.print("새 이름 입력: ");
        String newName = scanner.nextLine().trim();

        u.setUpdate(newName);
        userService.update(u);












    }
}
