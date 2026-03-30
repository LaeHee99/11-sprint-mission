package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.dto.request.MessageCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.UserCreateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Collections;
import java.util.Optional;

@SpringBootApplication
public class DiscodeitApplication {
	static User setupUser(UserService userService) {
		UserCreateRequest request = new UserCreateRequest("woody", "woody@codeit.com", "woody1234");
		return userService.create(request, Optional.empty());
	}

	static Channel setupChannel(ChannelService channelService) {
		PublicChannelCreateRequest request = new PublicChannelCreateRequest("공지", "공지 채널입니다.");
		return channelService.create(request);
	}

	static void messageCreateTest(MessageService messageService, Channel channel, User author) {
		MessageCreateRequest request = new MessageCreateRequest("안녕하세요.", channel.getId(), author.getId());
		// 빈 리스트를 더 안전하게 전달하기 위해 Collections.emptyList() 사용
		Message message = messageService.create(request, Collections.emptyList());
		System.out.println("메시지 생성: " + message.getId());
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DiscodeitApplication.class, args);

		// 서비스 초기화
		UserService userService = context.getBean(UserService.class);
		ChannelService channelService = context.getBean(ChannelService.class);
		MessageService messageService = context.getBean(MessageService.class);

		// 셋업 및 테스트
		try {
			// 셋업
			User user = setupUser(userService);
			Channel channel = setupChannel(channelService);
			// 테스트
			messageCreateTest(messageService, channel, user);
			System.out.println("테스트 데이터 세팅 성공");
		} catch (IllegalArgumentException e) {
			// 유저나 채널이 이미 존재해서 에러 날 시 그냥 넘어가기
			System.out.println("이미 테스트 데이터가 존재합니다. : " + e.getMessage());
		}

	}
}