package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.Control.ChannelControl;
import com.sprint.mission.discodeit.Control.MainControl;
import com.sprint.mission.discodeit.Control.MessageControl;
import com.sprint.mission.discodeit.Control.UserControl;
import com.sprint.mission.discodeit.menu.MainMenu;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;

import java.util.Scanner;

public class JavaApplication {
    public static void main(String[] args) {
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository);

        Scanner input = new Scanner(System.in);

        while(true) {
            MainMenu mainMenu = MainControl.selectMainMenu(input);

            switch (mainMenu) {
                case USER:
                    UserControl.controlUserMenu(input, userService);
                    break;
                case CHANNEL:
                    ChannelControl.controlChannelMenu(input, channelService);
                    break;
                case MESSAGE:
                    MessageControl.controlMessageMenu(input, messageService);
                    break;
                case EXIT:
                    System.out.println("프로그램을 종료합니다.");
                    return;
            }
        }
    }


}

