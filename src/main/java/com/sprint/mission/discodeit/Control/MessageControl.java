package com.sprint.mission.discodeit.Control;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.menu.MessageMenu;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class MessageControl {
    public static void controlMessageMenu(Scanner input, MessageService messageService) {
        while(true) {
            System.out.println(
                    "== Create(1) == Read(2) == ReadAll(3) == Delete(4) == Update(5) == Back(0)"
            );

            try {
                int num = input.nextInt();
                input.nextLine();
                MessageMenu menu = MessageMenu.from(num);

                switch(menu) {
                    case CREATE_MESSAGE:
                        try {
                            System.out.print("메세지를 입력해주세요 : ");
                            String createMessage = input.nextLine();
                            messageService.createMessage(createMessage);
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case READ_MESSAGE:
                        while(true) {
                            try {
                                List<Message> messageList = messageService.readAllMessage();
                                if(messageList.isEmpty()) {
                                    System.out.println("메세지 목록이 비어있습니다.");
                                    break;
                                }
                                for (int i = 0; i < messageList.size(); i++) {
                                    Message rm = messageList.get(i);
                                    System.out.println((i + 1) + ". 메세지 : " + rm.getMessage());
                                }
                                System.out.print("조회 할 메세지를 선택해주세요 : ");
                                int readMessageChoice = Integer.parseInt(input.nextLine());

                                if(readMessageChoice < 1 || readMessageChoice > messageList.size()) {
                                    System.out.println("목록에 없는 번호입니다. 다시 입력해주세요.");
                                    continue;
                                }
                                Message choiceMessage = messageList.get(readMessageChoice - 1);
                                UUID id = choiceMessage.getId();
                                Message resultMessage = messageService.readMessage(id);
                                System.out.println("조회 결과 : " + resultMessage.getMessage() + ", 마지막 업데이트 시간 : " + resultMessage.getUpdatedAt());
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("메세지가 존재하지 않습니다.");
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case READ_ALL:
                        try {
                            List<Message> allReadMessageList = messageService.readAllMessage();
                            if(allReadMessageList.isEmpty()) {
                                System.out.println("메세지 목록이 비어있습니다.");
                                break;
                            }
                            for (int i = 0; i < allReadMessageList.size(); i++) {
                                Message am = allReadMessageList.get(i);
                                System.out.println((i + 1) + ". 메세지 : " + am.getMessage());
                            }
                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case DELETE:
                        while(true) {
                            try {
                                List<Message> delMessageList = messageService.readAllMessage();
                                if(delMessageList.isEmpty()) {
                                    System.out.println("메세지 목록이 비어있습니다.");
                                    break;
                                }
                                for (int i = 0; i < delMessageList.size(); i++) {
                                    Message rm = delMessageList.get(i);
                                    System.out.println((i + 1) + ". 메세지 : " + rm.getMessage());
                                }
                                System.out.println("삭제 할 메세지를 선택해주세요.");
                                int choice = Integer.parseInt(input.nextLine());
                                if(choice < 1 || choice > delMessageList.size()) {
                                    System.out.println("목록에 없는 번호입니다. 다시 입력해주세요.");
                                    continue;
                                }
                                Message delMessage = delMessageList.get(choice - 1);
                                UUID delId = delMessage.getId();

                                messageService.deleteMessage(delId);
                                System.out.println("삭제되었습니다.");
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("메세지가 존재하지 않습니다.");
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case UPDATE:
                        while(true) {
                            try {
                                List<Message> updateMessageList = messageService.readAllMessage();
                                if(updateMessageList.isEmpty()) {
                                    System.out.println("메세지 목록이 비어있습니다.");
                                    break;
                                }
                                for (int i = 0; i < updateMessageList.size(); i++) {
                                    Message um = updateMessageList.get(i);
                                    System.out.println((i + 1) + ". 메세지 : " + um.getMessage());
                                }
                                System.out.print("변경 할 메세지를 선택해주세요 : ");
                                int updateChoice = Integer.parseInt(input.nextLine());
                                if(updateChoice < 1 || updateChoice > updateMessageList.size()) {
                                    System.out.println("목록에 없는 번호입니다. 다시 입력해주세요.");
                                    continue;
                                }
                                Message updateMessage = updateMessageList.get(updateChoice - 1);
                                UUID updateId = updateMessage.getId();

                                System.out.print("변경 할 메세지를 입력해주세요 : ");
                                String newMessage = input.nextLine();

                                messageService.updateMessage(updateId, newMessage);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("업데이트 할 메세지가 존재하지 않습니다.");
                            } catch (RuntimeException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case BACK:
                        System.out.println("메인메뉴로 돌아갑니다.");
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("입력이 잘못되었습니다. 다시 입력하세요.");
                input.nextLine();
            }
        }
    }
}
