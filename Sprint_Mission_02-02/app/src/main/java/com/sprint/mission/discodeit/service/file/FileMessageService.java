package com.sprint.mission.discodeit.service.file;

import java.util.List;
import java.util.UUID;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.service.ChannelMemberService;
import com.sprint.mission.discodeit.service.MessageService;

public class FileMessageService implements MessageService {
    
    private static FileMessageService instance;

    private final MessageRepository messageRepository;
    private final ChannelMemberService channelMemberService;

    private FileMessageService(MessageRepository messageRepository, ChannelMemberService channelMemberService) {
        this.messageRepository = messageRepository;
        this.channelMemberService = channelMemberService;
    }

    public static FileMessageService getInstance(MessageRepository messageRepository, ChannelMemberService channelMemberService) {
        if (instance == null) {
            instance = new FileMessageService(messageRepository, channelMemberService);
        }
        return instance;
    }

    //
    @Override
    public Message sendMessage(UUID channelId, UUID userId, String content) {
        if (!channelMemberService.isMember(channelId, userId)) {
            throw new RuntimeException("해당 채널에 가입된 멤버만 메시지를 보낼 수 있습니다.");
        }

        Message newMessage = new Message(content, userId, channelId);
        return messageRepository.save(newMessage);
    }

    @Override
    public List<Message> getMessages(UUID channelId, UUID userId) {
        if (!channelMemberService.isMember(channelId, userId)) {
            throw new RuntimeException("해당 채널의 멤버만 대화 내용을 볼 수 있습니다.");
        }
        return messageRepository.findAllByChannelId(channelId);
    }

    @Override
    public Message updateMessage(UUID messageId, UUID userId, String newContent) {
        Message oldMessage = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 메세지입니다."));

        if (!oldMessage.getUserId().equals(userId)) {
            throw new RuntimeException("본인이 작성한 메시지만 수정할 수 있습니다.");
        }

        Message updatedMessage = oldMessage.updateContent(newContent);
        return messageRepository.save(updatedMessage);
    }

    @Override
    public void deleteMessage(UUID messageId, UUID userId) {
        Message message = messageRepository.findById(messageId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 메시지입니다."));

        if (!message.getUserId().equals(userId)) {
            throw new RuntimeException("본인이 작성한 메시지만 삭제할 수 있습니다.");
        }

        messageRepository.deleteById(message.getId());
    }

}
